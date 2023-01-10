package suvorov.coin.ui.currency

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import suvorov.coin.R
import suvorov.coin.databinding.FragmentCurrencyBinding
import suvorov.coin.domain.model.Currency
import suvorov.coin.ui.base.BaseFragment
import suvorov.coin.util.*
import suvorov.coin.util.ChartHelper.displayLineGraphic
import suvorov.coin.util.ShowHelper.showToast

class CurrencyFragment: BaseFragment<FragmentCurrencyBinding>(FragmentCurrencyBinding::inflate) {

    private val viewModel: CurrencyViewModel by viewModel()
    private val args: CurrencyFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        viewModel.getCurrencyHistory(args.id)
    }

    private fun observeViewModel() {
        viewModel.currencyById(args.id).observe(viewLifecycleOwner) {
            initView(it)
        }

        viewModel.currencyHistory.observe(viewLifecycleOwner) {
            displayLineGraphic(binding.currencyChart.lineChart, it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.dataError.observe(viewLifecycleOwner) {
            if(it) showToast(requireContext(), getString(R.string.data_loading_error))
        }
    }

    private fun initView(model: Currency) {
        binding.apply {
            toolbar.setOnClickListener {
                findNavController().popBackStack()
            }
            imageView.load(model.image)
            nameTextView.text = model.name
            priceTextView.text = model.price.priceDollarString()
            PriceHelper.showPriceChange(changePriceTextView, model.priceChange)

            portfolioImageView.setImageResource(
                if (model.isPortfolio) R.drawable.star_icon
                else R.drawable.star_border_icon
            )

            portfolioImageView.setOnClickListener {
                viewModel.updatePortfolioStatus(model.id)
                showToast(
                    requireContext(),
                    if (model.isPortfolio)
                        "${getString(R.string.coin)} ${model.name} ${getString(R.string.removed_from_portfolio)}"
                    else
                        "${getString(R.string.coin)} ${model.name} ${getString(R.string.add_to_portfolio)}"
                )
            }
        }
    }
}