package suvorov.coin.ui.login

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import suvorov.coin.R
import suvorov.coin.databinding.FragmentLoginBinding
import suvorov.coin.ui.base.BaseFragment
import suvorov.coin.util.ShowHelper.showToast

class LoginFragment: BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding.loginButton.setOnClickListener {
            login()
        }
        binding.registrationTextView.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToRegistrationFragment())
        }
        binding.toolbar.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun login() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if (email.isBlank() || password.isBlank()) {
            showToast(requireContext(), getString(R.string.email_not_empty))
            return
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity()) {
            if (it.isSuccessful) {
                showToast(requireContext(), getString(R.string.successful_account_login))
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToSuccessfulLoginFragment(email)
                )
            } else
                showToast(requireContext(), getString(R.string.log_in_failed))
        }
    }
}