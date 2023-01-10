package suvorov.coin.ui.sort

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import suvorov.coin.R
import suvorov.coin.databinding.FragmentSortBinding
import suvorov.coin.domain.repository.PreferenceStorage
import suvorov.coin.ui.base.BaseBottomSheetDialogFragment
import suvorov.coin.ui.base.SharedViewModel

class SortFragment: BaseBottomSheetDialogFragment<FragmentSortBinding>(FragmentSortBinding::inflate) {

    private val sharedViewModel: SharedViewModel by activityViewModel()
    private val preferenceStorage: PreferenceStorage by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadCheckedRadioButton()
        initView()
    }

    private fun initView() {
        binding.radioGroup.setOnCheckedChangeListener { _, i ->
            val result = when (i) {
                R.id.ratingButton -> binding.ratingTextView.text.toString()
                R.id.capButton -> binding.capTextView.text.toString()
                R.id.changeButton -> binding.changeTextView.text.toString()
                R.id.priceButton-> binding.priceTextView.text.toString()
                else -> "button is missing"
            }
            sharedViewModel.saveCurrenciesSort(result)
            saveCheckedRadioButton(i)
        }
    }

    private fun saveCheckedRadioButton(i: Int) {
        val checkedRadioButton = binding.radioGroup.findViewById<RadioButton>(i)
        val checkedIndex = binding.radioGroup.indexOfChild(checkedRadioButton)
        preferenceStorage.currenciesSort = checkedIndex
    }

    private fun loadCheckedRadioButton() {
        val savedCheckedRadioButton =
            binding.radioGroup.getChildAt(preferenceStorage.currenciesSort) as RadioButton
        savedCheckedRadioButton.isChecked = true
    }
}