package suvorov.coin.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import suvorov.coin.databinding.FragmentSearchBinding
import suvorov.coin.domain.model.Currency
import suvorov.coin.ui.base.BaseFragment
import suvorov.coin.ui.currencies.CurrenciesViewModel
import suvorov.coin.ui.currencies.adapter.clicklistener.CurrenciesClickListener
import suvorov.coin.ui.search.adapter.SearchAdapter

class SearchFragment:
    BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),
    SearchView.OnQueryTextListener,
    CurrenciesClickListener
{
    private val viewModel: CurrenciesViewModel by viewModel()
    private val searchAdapter by lazy { SearchAdapter(this) }
    private var currenciesList = listOf<Currency>()
    private var searchQuery = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        observeViewModel()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun observeViewModel() {
        viewModel.currenciesListFromDatabase.observe(viewLifecycleOwner) {
            if(searchQuery.isNotEmpty()) searchAdapter.submitList(loadData(it, searchQuery))
            currenciesList = it
        }
    }

    private fun loadData(list: List<Currency>, query: String): List<Currency> {
        val currencies = arrayListOf<Currency>()
        list.forEach { currency ->
            if(currency.name.contains(query, true)) {
                currencies.add(currency)
            }
        }
        return currencies
    }

    private fun initView() {
        binding.searchView.setOnQueryTextListener(this)
        binding.searchRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchAdapter
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText != null) {
            searchAdapter.submitList(loadData(currenciesList, newText))
            searchQuery = newText
        }
        if(newText.isNullOrEmpty()) {
            searchAdapter.submitList(null)
        }
        return true
    }

    override fun onCurrencyClick(id: String) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToCurrencyFragment(id))
    }
}