package suvorov.coin.data.local.firebase

import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import suvorov.coin.domain.model.Settings
import suvorov.coin.domain.repository.FirebaseDatabase

class CoinFirebaseDatabase: FirebaseDatabase {
    private val database = Firebase.database.reference

    private val _settingsFlow = MutableStateFlow(Settings())
    override val settingsFlow: StateFlow<Settings> = _settingsFlow

    override fun read() {
        database.get().addOnSuccessListener {
            _settingsFlow.value = it.getValue<Settings>() ?: Settings()
        }
    }

    override fun update(locale: Int, screen: Int, notification: Boolean) {
        val settings = Settings(locale, screen, notification)
        val mapSettings = hashMapOf<String, Any>(
            "locale" to settings.locale,
            "screen" to settings.screen,
            "notification" to settings.notification
        )
        database.updateChildren(mapSettings)
    }
}