package suvorov.coin.ui.registration

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import suvorov.coin.R
import suvorov.coin.databinding.FragmentRegistrationBinding
import suvorov.coin.ui.base.BaseFragment
import suvorov.coin.util.ShowHelper.showToast

class RegistrationFragment:
    BaseFragment<FragmentRegistrationBinding>(FragmentRegistrationBinding::inflate)
{
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        binding.registrationButton.setOnClickListener {
            signUpUser()
        }
        binding.toolbar.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun signUpUser() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val repeatPassword = binding.repeatPasswordEditText.text.toString()

        if (email.isBlank() || password.isBlank() || repeatPassword.isBlank()) {
            showToast(requireContext(), getString(R.string.email_not_empty))
            return
        }

        if(password.length != 6) {
            showToast(requireContext(), getString(R.string.password_length))
            return
        }

        if (password != repeatPassword) {
            showToast(requireContext(), getString(R.string.password_does_not_match))
            return
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity()) {
            if (it.isSuccessful) {
                showToast(requireContext(), getString(R.string.successful_registration))
                findNavController().navigate(
                    RegistrationFragmentDirections.actionRegistrationFragmentToCheckRegistrationFragment()
                )
            }else {
                showToast(requireContext(), getString(R.string.invalid_password))
            }
        }
    }
}