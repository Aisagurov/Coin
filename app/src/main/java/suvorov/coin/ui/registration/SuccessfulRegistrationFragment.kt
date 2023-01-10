package suvorov.coin.ui.registration

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import suvorov.coin.databinding.FragmentSuccessfulRegistrationBinding
import suvorov.coin.ui.base.BaseFragment

class SuccessfulRegistrationFragment:
    BaseFragment<FragmentSuccessfulRegistrationBinding>(FragmentSuccessfulRegistrationBinding::inflate)
{
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.closeButton.setOnClickListener {
            findNavController().navigate(
                SuccessfulRegistrationFragmentDirections.actionCheckRegistrationFragmentToLoginFragment()
            )
        }
    }
}