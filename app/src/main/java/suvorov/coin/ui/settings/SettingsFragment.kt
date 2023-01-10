package suvorov.coin.ui.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.recreate
import androidx.navigation.fragment.findNavController
import org.koin.android.ext.android.inject
import suvorov.coin.R
import suvorov.coin.databinding.FragmentSettingsBinding
import suvorov.coin.domain.repository.PreferenceStorage
import suvorov.coin.ui.base.BaseFragment
import java.util.*

class SettingsFragment: BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    private val preferenceStorage: PreferenceStorage by inject()

    companion object {
        const val ENGLISH_LANGUAGE = "en"
        const val RUSSIAN_LANGUAGE = "ru"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadLocale()
        loadTitleScreen()
        loadNotificationSwitch()
        initLocaleView()
        initDefaultScreenView()
        initNotificationView()
        initRegistrationView()
    }

    private fun initRegistrationView() {
        binding.loginTextView.setOnClickListener {
            findNavController().navigate(
                SettingsFragmentDirections.actionSettingsFragmentToLoginFragment()
            )
        }
        binding.signUpTextView.setOnClickListener {
            findNavController().navigate(
                SettingsFragmentDirections.actionSettingsFragmentToRegistrationFragment()
            )
        }
    }

    private fun initLocaleView() {
        binding.languageTextView.setOnClickListener {
            val languages = arrayOf(getString(R.string.english), getString(R.string.russian))
            val langSelectorBuilder = AlertDialog.Builder(requireContext(), R.style.DialogTheme)

            langSelectorBuilder.setSingleChoiceItems(
                languages, preferenceStorage.alertDialogLocale
            ) { dialog, selection ->

                when(selection) {
                    0 -> setLocale(ENGLISH_LANGUAGE)
                    1 -> setLocale(RUSSIAN_LANGUAGE)
                }
                preferenceStorage.alertDialogLocale = selection
                recreate(requireActivity())
                dialog.dismiss()
            }
            langSelectorBuilder.create().show()
        }
    }

    private fun initDefaultScreenView() {
        binding.defaultScreenTextView.setOnClickListener {
            val screens = arrayOf(getString(R.string.title_market), getString(R.string.title_portfolio))
            val screenSelectorBuilder = AlertDialog.Builder(requireContext(), R.style.DialogTheme)

            screenSelectorBuilder.setSingleChoiceItems(
                screens, preferenceStorage.alertDialogScreen
            ) { dialog, selection ->
                preferenceStorage.alertDialogScreen = selection
                recreate(requireActivity())
                dialog.dismiss()
            }
            screenSelectorBuilder.create().show()
        }
    }

    private fun setLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        resources.configuration.setLocale(locale)
        resources.configuration.setLayoutDirection(locale)
        preferenceStorage.locale = language
    }

    private fun initNotificationView() {
        binding.notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            preferenceStorage.notificationSwitch = isChecked
            recreate(requireActivity())
        }
    }

    private fun loadLocale() {
        val localeToSet = preferenceStorage.locale
        localeToSet?.let { setLocale(it) }
        when(localeToSet) {
            ENGLISH_LANGUAGE -> binding.languageTextView.text = getString(R.string.english)
            RUSSIAN_LANGUAGE -> binding.languageTextView.text = getString(R.string.russian)
        }
    }

    private fun loadTitleScreen() {
        when(preferenceStorage.alertDialogScreen) {
            0 -> binding.defaultScreenTextView.text = getString(R.string.title_market)
            1 -> binding.defaultScreenTextView.text = getString(R.string.title_portfolio)
        }
    }

    private fun loadNotificationSwitch() {
        binding.notificationsSwitch.isChecked = preferenceStorage.notificationSwitch
    }
}