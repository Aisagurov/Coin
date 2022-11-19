package suvorov.coin.data.local.preferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface PreferenceStorage {
    var timeLoadedAt: Long
}

class SharedPreferenceStorage(application: Application): PreferenceStorage {
    private val preferences: Lazy<SharedPreferences> = lazy {
        application.applicationContext.getSharedPreferences("COINS_PREFERENCES", Context.MODE_PRIVATE)
    }

    override var timeLoadedAt by LongPreferences(preferences, "LAST_LOAD_TIME", 0)
}

class LongPreferences(
    private val preferences: Lazy<SharedPreferences>,
    private val name: String,
    private val defaultValue: Long
): ReadWriteProperty<Any, Long> {

    override fun getValue(thisRef: Any, property: KProperty<*>): Long {
        return preferences.value.getLong(name, defaultValue)
    }
    override fun setValue(thisRef: Any, property: KProperty<*>, value: Long) {
        preferences.value.edit { putLong(name, value) }
    }
}