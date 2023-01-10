package suvorov.coin.domain.repository

import kotlinx.coroutines.flow.SharedFlow
import suvorov.coin.domain.model.Settings

interface FirebaseDatabase {
    val settingsFlow: SharedFlow<Settings>
    fun read()
    fun update(locale: Int, screen: Int, notification: Boolean)
}