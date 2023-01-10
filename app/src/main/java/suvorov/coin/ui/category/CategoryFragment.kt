package suvorov.coin.ui.category

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import suvorov.coin.databinding.FragmentCategoryBinding
import suvorov.coin.domain.model.Category
import suvorov.coin.ui.base.BaseFragment
import suvorov.coin.util.*

class CategoryFragment: BaseFragment<FragmentCategoryBinding>(FragmentCategoryBinding::inflate) {

    private val viewModel: CategoryViewModel by viewModel()
    private val args: CategoryFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.categoryById(args.id).observe(viewLifecycleOwner) {
            initView(it)
        }
    }

    private fun initView(model: Category) {
        binding.apply {
            toolbar.setOnClickListener {
                findNavController().popBackStack()
            }
            firstImageView.load(model.firstTop)
            secondImageView.load(model.secondTop)
            thirdImageView.load(model.thirdTop)
            nameTextView.text = model.name
            marketCupTextView.text = model.marketCap.dollarString()
            capChangeTextView.text = model.change.percentString()
            volumeTextView.text = model.volume.dollarString()
        }
    }
}