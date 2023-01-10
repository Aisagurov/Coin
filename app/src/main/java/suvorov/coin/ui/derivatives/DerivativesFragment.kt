package suvorov.coin.ui.derivatives

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import suvorov.coin.databinding.FragmentDerivativesBinding
import suvorov.coin.ui.base.BaseFragment
import suvorov.coin.ui.derivatives.adapter.DerivativesAdapter
import suvorov.coin.ui.derivatives.adapter.clicklistener.DerivativesClickListener
import suvorov.coin.ui.market.MarketFragmentDirections

class DerivativesFragment:
    BaseFragment<FragmentDerivativesBinding>(FragmentDerivativesBinding::inflate),
    DerivativesClickListener
{
    private val viewModel: DerivativesViewModel by viewModel()
    private val derivativesAdapter by lazy { DerivativesAdapter(this) }

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
        viewModel.getDerivativesFromApi()
        initView()
    }

    private fun observeViewModel() {
        viewModel.derivativesListFromDatabase.observe(viewLifecycleOwner) {
            derivativesAdapter.submitList(it)

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

    private fun initView() {
        binding.derivativesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = derivativesAdapter
        }

        binding.repeatButton.setOnClickListener {
            viewModel.getDerivativesFromApi()
        }
    }

    override fun onDerivativeClick(name: String) {
        findNavController()
            .navigate(MarketFragmentDirections.actionMarketFragmentToDerivativeFragment(name))
    }
}