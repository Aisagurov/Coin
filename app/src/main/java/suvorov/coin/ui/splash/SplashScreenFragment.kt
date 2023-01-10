package suvorov.coin.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.navigation.fragment.findNavController
import org.koin.android.ext.android.inject
import suvorov.coin.databinding.FragmentSplashBinding
import suvorov.coin.domain.repository.PreferenceStorage
import suvorov.coin.ui.base.BaseFragment

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment: BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val preferenceStorage: PreferenceStorage by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({

            if(preferenceStorage.alertDialogScreen == 1) {
                findNavController().navigate(
                    SplashScreenFragmentDirections.actionSplashScreenFragmentToPortfolioFragment())

            } else {
                findNavController().navigate(
                    SplashScreenFragmentDirections.actionSplashScreenFragmentToMarketFragment())
            }
        }, 3000)
    }
}