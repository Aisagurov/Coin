package suvorov.coin.ui.login

import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import suvorov.coin.R
import suvorov.coin.databinding.FragmentSuccessfulLoginBinding
import suvorov.coin.domain.repository.PreferenceStorage
import suvorov.coin.ui.base.BaseFragment
import suvorov.coin.ui.settings.SettingsFragment.Companion.ENGLISH_LANGUAGE
import suvorov.coin.ui.settings.SettingsFragment.Companion.RUSSIAN_LANGUAGE
import suvorov.coin.util.ShowHelper.showToast

class SuccessfulLoginFragment:
    BaseFragment<FragmentSuccessfulLoginBinding>(FragmentSuccessfulLoginBinding::inflate)
{
    private val viewModel: LoginViewModel by viewModel()
    private val args: SuccessfulLoginFragmentArgs by navArgs()
    private val preferenceStorage: PreferenceStorage by inject()
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        initView()
    }

    private fun initView() {
        binding.toolbar.setOnClickListener {
            findNavController().navigate(
                SuccessfulLoginFragmentDirections.actionSuccessfulLoginFragmentToSettingsFragment()
            )
        }
        binding.emailTextView.text = args.email

        binding.saveTextView.setOnClickListener {
            viewModel.update(
                preferenceStorage.alertDialogLocale,
                preferenceStorage.alertDialogScreen,
                preferenceStorage.notificationSwitch
            )
            showToast(requireContext(), getString(R.string.settings_saved))
        }

        binding.restoreTextView.setOnClickListener {
            viewModel.settings.observe(viewLifecycleOwner) {
                when(it.locale) {
                    0 -> {
                        preferenceStorage.locale = ENGLISH_LANGUAGE
                        ActivityCompat.recreate(requireActivity())
                    }
                    1 -> {
                        preferenceStorage.locale = RUSSIAN_LANGUAGE
                        ActivityCompat.recreate(requireActivity())
                    }
                }
                preferenceStorage.alertDialogLocale = it.locale
                preferenceStorage.alertDialogScreen = it.screen
                preferenceStorage.notificationSwitch = it.notification
            }
            showToast(requireContext(), getString(R.string.settings_restored))
        }

        binding.deleteTextView.setOnClickListener {
            auth.currentUser?.delete()?.addOnCompleteListener {
                if(it.isSuccessful) {
                    showToast(requireContext(), getString(R.string.account_deleted))
                    findNavController().navigate(
                        SuccessfulLoginFragmentDirections.actionSuccessfulLoginFragmentToSettingsFragment()
                    )
                }
            }
        }
    }
}