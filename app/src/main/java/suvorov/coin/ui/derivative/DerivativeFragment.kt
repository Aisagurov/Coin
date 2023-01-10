package suvorov.coin.ui.derivative

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import suvorov.coin.databinding.FragmentDerivativeBinding
import suvorov.coin.domain.model.Derivative
import suvorov.coin.ui.base.BaseFragment
import suvorov.coin.util.load

class DerivativeFragment: BaseFragment<FragmentDerivativeBinding>(FragmentDerivativeBinding::inflate) {

    private val viewModel: DerivativeViewModel by viewModel()
    private val args: DerivativeFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.derivativeByName(args.name).observe(viewLifecycleOwner) {
            initView(it)
        }
    }

    private fun initView(model: Derivative) {
        binding.apply {
            toolbar.setOnClickListener {
                findNavController().popBackStack()
            }
            imageView.load(model.image)
            nameTextView.text = model.name
            urlTextView.text = model.url
            yearTextView.text = model.year.toString()
            numberFuturesTextView.text = model.numberFutures.toString()
            numberPerpetualTextView.text = model.numberPerpetual.toString()
        }
    }
}