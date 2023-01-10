package suvorov.coin.ui

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject
import suvorov.coin.R
import suvorov.coin.domain.repository.PreferenceStorage
import suvorov.coin.util.ConnectionType
import suvorov.coin.util.NetworkMonitor
import suvorov.coin.util.ShowHelper.showToast
import suvorov.coin.worker.CoinWorkManager
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity: AppCompatActivity() {

    private val networkMonitor = NetworkMonitor(this)
    private val preferenceStorage: PreferenceStorage by inject()

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ContextWrapper(preferenceStorage.locale?.let { language ->
            newBase.setLocale(language)
        }))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavigation()
        initReceiver()
        initWorker()
    }

    override fun onResume() {
        super.onResume()
        networkMonitor.register()
    }

    override fun onStop() {
        super.onStop()
        networkMonitor.unregister()
    }

    private fun Context.setLocale(language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return createConfigurationContext(configuration)
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val mainBottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        mainBottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(
                destination.id == R.id.splashScreenFragment ||
                destination.id == R.id.currencyFragment ||
                destination.id == R.id.categoryFragment ||
                destination.id == R.id.exchangeFragment ||
                destination.id == R.id.derivativeFragment ||
                destination.id == R.id.registrationFragment ||
                destination.id == R.id.loginFragment
            ) {
                mainBottomNavigationView.visibility = View.GONE
            } else {
                mainBottomNavigationView.visibility = View.VISIBLE
            }
        }
    }

    private fun initReceiver() {
        networkMonitor.result = { isAvailable, type ->
            runOnUiThread {
                when (isAvailable) {
                    true -> {
                        when (type) {
                            ConnectionType.Wifi -> {
                                Log.i("NETWORK_MONITOR_STATUS", "Wifi Connection")
                            }
                            ConnectionType.Cellular -> {
                                Log.i("NETWORK_MONITOR_STATUS", "Cellular Connection")
                            }
                            else -> { }
                        }
                    }
                    false -> {
                        showToast(this, getString(R.string.no_internet))
                        Log.i("NETWORK_MONITOR_STATUS", "No Connection")
                    }
                }
            }
        }
    }

    private fun initWorker() {
        if(preferenceStorage.notificationSwitch)
            WorkManager.getInstance(this).enqueue(buildWorkRequest())
        else
            WorkManager.getInstance(this).cancelWorkById(buildWorkRequest().id)
    }

    private fun buildWorkRequest(): WorkRequest {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        return PeriodicWorkRequestBuilder<CoinWorkManager>(24, TimeUnit.HOURS)
            .setInitialDelay(1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()
    }
}