package suvorov.coin.ui.exchange

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import suvorov.coin.R
import suvorov.coin.domain.model.Exchange
import suvorov.coin.databinding.FragmentExchangeBinding
import suvorov.coin.ui.base.BaseFragment
import suvorov.coin.util.*
import suvorov.coin.util.ShowHelper.showToast

class ExchangeFragment: BaseFragment<FragmentExchangeBinding>(FragmentExchangeBinding::inflate) {

    private val viewModel: ExchangeViewModel by viewModel()
    private val args: ExchangeFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        viewModel.getExchangeHistory(args.id)
    }

    private fun observeViewModel() {
        viewModel.exchangeById(args.id).observe(viewLifecycleOwner) {
            initView(it)
        }

        viewModel.exchangeHistory.observe(viewLifecycleOwner) {
            ChartHelper.displayLineGraphic(binding.exchangeChart.lineChart, it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.dataError.observe(viewLifecycleOwner) {
            if(it) showToast(requireContext(), getString(R.string.data_loading_error))
        }
    }

    private fun initView(model: Exchange) {
        binding.apply {
            toolbar.setOnClickListener {
                findNavController().popBackStack()
            }
            imageView.load(model.image)
            nameTextView.text = model.name
            volumeTextView.text = model.volume.btcString()
        }
    }
}