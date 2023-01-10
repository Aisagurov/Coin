package suvorov.coin.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import suvorov.coin.databinding.FragmentCategoriesBinding
import suvorov.coin.ui.base.BaseFragment
import suvorov.coin.ui.categories.adapter.CategoriesAdapter
import suvorov.coin.ui.categories.adapter.clicklistener.CategoriesClickListener
import suvorov.coin.ui.market.MarketFragmentDirections

class CategoriesFragment:
    BaseFragment<FragmentCategoriesBinding>(FragmentCategoriesBinding::inflate),
    CategoriesClickListener
{
    private val viewModel: CategoriesViewModel by viewModel()
    private val categoriesAdapter by lazy { CategoriesAdapter(this) }

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
        viewModel.getCategoriesFromApi()
        initView()
    }

    private fun observeViewModel() {
        viewModel.categoriesListFromDatabase.observe(viewLifecycleOwner) {
            categoriesAdapter.submitList(it.sortedByDescending { item -> item.change })

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
        binding.categoriesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = categoriesAdapter
        }

        binding.repeatButton.setOnClickListener {
            viewModel.getCategoriesFromApi()
        }
    }

    override fun onCategoryClick(id: String) {
        findNavController().navigate(
            MarketFragmentDirections.actionMarketFragmentToCategoryFragment(id)
        )
    }
}