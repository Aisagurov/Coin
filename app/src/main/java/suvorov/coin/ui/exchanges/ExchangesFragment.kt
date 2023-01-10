package suvorov.coin.ui.exchanges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import suvorov.coin.databinding.FragmentExchangesBinding
import suvorov.coin.ui.base.BaseFragment
import suvorov.coin.ui.exchanges.adapter.ExchangesAdapter
import suvorov.coin.ui.exchanges.adapter.clicklistener.ExchangesClickListener
import suvorov.coin.ui.market.MarketFragmentDirections

class ExchangesFragment:
    BaseFragment<FragmentExchangesBinding>(FragmentExchangesBinding::inflate),
    ExchangesClickListener
{
    private val viewModel: ExchangesViewModel by viewModel()
    private val exchangesAdapter by lazy { ExchangesAdapter(this) }

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
        viewModel.getExchangesFromApi()
        initViw()
    }

    private fun observeViewModel() {
        viewModel.exchangesListFromDatabase.observe(viewLifecycleOwner) {
            exchangesAdapter.submitList(it.sortedBy { item -> item.rank })

            binding.noInternetTextView.visibility =
                if(viewModel.isListEmpty()) View.VISIBLE else View.GONE

            binding.repeatButton.visibility =
                if(viewModel.isListEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility =
                if (viewModel.isListEmpty() && it) View.VISIBLE else View.GONE
        }
    }

    private fun initViw() {
        binding.exchangesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = exchangesAdapter
        }

        binding.repeatButton.setOnClickListener {
            viewModel.getExchangesFromApi()
        }
    }

    override fun onExchangeClick(id: String) {
        findNavController()
            .navigate(MarketFragmentDirections.actionMarketFragmentToExchangeFragment(id))
    }
}