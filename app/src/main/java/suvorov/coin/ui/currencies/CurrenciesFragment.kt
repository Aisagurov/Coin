package suvorov.coin.ui.currencies

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import suvorov.coin.R
import suvorov.coin.databinding.FragmentCurrenciesBinding
import suvorov.coin.domain.model.Currency
import suvorov.coin.domain.model.Global
import suvorov.coin.domain.repository.PreferenceStorage
import suvorov.coin.ui.base.BaseFragment
import suvorov.coin.ui.base.SharedViewModel
import suvorov.coin.ui.currencies.adapter.CurrenciesAdapter
import suvorov.coin.ui.currencies.adapter.clicklistener.CurrenciesClickListener
import suvorov.coin.ui.market.MarketFragmentDirections
import suvorov.coin.ui.sort.SortFragment
import suvorov.coin.util.*
import suvorov.coin.util.ShowHelper.showToast

class CurrenciesFragment:
    BaseFragment<FragmentCurrenciesBinding>(FragmentCurrenciesBinding::inflate),
    CurrenciesClickListener
{
    private val sharedViewModel: SharedViewModel by activityViewModel()
    private val viewModel: CurrenciesViewModel by viewModel()
    private val preferenceStorage: PreferenceStorage by inject()
    private val currenciesAdapter by lazy { CurrenciesAdapter(this) }
    private var currenciesList = listOf<Currency>()
    private var currenciesSort = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        viewModel.getCurrenciesFromApi()
        viewModel.getGlobalFromApi()
        saveValueRadioGroup()
        initCurrenciesView()
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility =
                if (viewModel.isListEmpty() && it) View.VISIBLE else View.GONE
        }

        viewModel.currenciesListFromDatabase.observe(viewLifecycleOwner) {
            currenciesAdapter.submitList(loadData(it, currenciesSort))
            currenciesList = it

            binding.noInternetTextView.visibility =
                if(viewModel.isListEmpty()) View.VISIBLE else View.GONE

            binding.repeatButton.visibility =
                if(viewModel.isListEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.globalFromDatabase.observe(viewLifecycleOwner) {
            initGlobalView(it)
        }

        sharedViewModel.currenciesSort.observe(viewLifecycleOwner) {
            currenciesAdapter.submitList(loadData(currenciesList, it))
            binding.sortTextView.text = it
            currenciesSort = it
        }
    }

    private fun loadData(list: List<Currency>, sort: String): List<Currency> {
        val currencies = arrayListOf<Currency>()
        for(currency in list) {
            if(currency.rank in 1..50) {
                currencies.add(currency)
            }
        }
        when(sort) {
            getString(R.string.rating) -> currencies.sortBy { it.rank }
            getString(R.string.cap) -> currencies.sortByDescending { it.cap }
            getString(R.string.change) -> currencies.sortByDescending { it.priceChange }
            getString(R.string.price) -> currencies.sortByDescending { it.price }
        }
        return currencies
    }

    private fun initCurrenciesView() {
        binding.currenciesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = currenciesAdapter

            object: SwipeHelper(requireContext() , binding.currenciesRecyclerView, false) {
                override fun instantiateUnderlayButton(
                    viewHolder: RecyclerView.ViewHolder?,
                    underlayButtons: MutableList<UnderlayButton>?
                ) {
                    val item = currenciesAdapter.currentList[viewHolder?.adapterPosition ?: 0]
                    underlayButtons?.add(UnderlayButton(
                        ContextCompat.getDrawable(
                            requireContext(),
                            if(item.isPortfolio) {
                                R.drawable.star_icon
                            } else {
                                R.drawable.star_border_icon
                            }
                        ),
                        ContextCompat.getColor(requireContext(), R.color.green),
                        object: UnderlayButtonClickListener {
                            override fun onClick(position: Int) {
                                viewModel.updatePortfolioStatus(item.id)
                                showToast(requireContext(),
                                    if (item.isPortfolio) {
                                        "${getString(R.string.coin)} ${item.name} ${getString(R.string.removed_from_portfolio)}"
                                    } else {
                                        "${getString(R.string.coin)} ${item.name} ${getString(R.string.add_to_portfolio)}"
                                    }
                                )
                            }
                        }
                    ))
                }
            }
        }

        binding.repeatButton.setOnClickListener {
            viewModel.getCurrenciesFromApi()
            viewModel.getGlobalFromApi()
        }

        binding.sortCardView.setOnClickListener {
            val sortFragment = SortFragment()
            sortFragment.show(requireActivity().supportFragmentManager, sortFragment.tag)
        }
    }

    private fun initGlobalView(model: Global) {
        binding.defiCapTextView.text = model.cap.dollarString()
        binding.volumeTextView.text = model.volume.dollarString()
        binding.dominanceTextView.text = model.dominance.percentString()
    }

    private fun saveValueRadioGroup() {
        when(preferenceStorage.currenciesSort) {
            0 -> binding.sortTextView.text = getString(R.string.rating)
            1 -> binding.sortTextView.text = getString(R.string.cap)
            2 -> binding.sortTextView.text = getString(R.string.change)
            3 -> binding.sortTextView.text = getString(R.string.price)
        }
    }

    override fun onCurrencyClick(id: String) {
        findNavController().navigate(
            MarketFragmentDirections.actionMarketFragmentToCurrencyFragment(id)
        )
    }
}