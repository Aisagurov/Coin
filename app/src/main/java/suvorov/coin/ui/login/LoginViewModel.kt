package suvorov.coin.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import suvorov.coin.domain.repository.FirebaseDatabase

class LoginViewModel(private val database: FirebaseDatabase): ViewModel() {

    val settings = database.settingsFlow.asLiveData()

    fun read() {
        database.read()
    }

    fun update(locale: Int, screen: Int, notification: Boolean) {
        database.update(locale, screen, notification)
    }
}