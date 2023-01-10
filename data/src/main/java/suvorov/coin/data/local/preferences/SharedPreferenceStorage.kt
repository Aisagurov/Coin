package suvorov.coin.data.local.preferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import suvorov.coin.data.local.preferences.PreferencesConstants.ALERT_DIALOG_LOCALE
import suvorov.coin.data.local.preferences.PreferencesConstants.ALERT_DIALOG_SCREEN
import suvorov.coin.data.local.preferences.PreferencesConstants.CURRENCIES_SORT
import suvorov.coin.data.local.preferences.PreferencesConstants.LOCALE_TO_SET
import suvorov.coin.data.local.preferences.PreferencesConstants.NOTIFICATION_SWITCH
import suvorov.coin.data.local.preferences.PreferencesConstants.TIME_CATEGORY
import suvorov.coin.data.local.preferences.PreferencesConstants.TIME_CURRENCY
import suvorov.coin.data.local.preferences.PreferencesConstants.TIME_DERIVATIVE
import suvorov.coin.data.local.preferences.PreferencesConstants.TIME_EXCHANGE
import suvorov.coin.data.local.preferences.PreferencesConstants.TIME_GLOBAL
import suvorov.coin.domain.repository.PreferenceStorage

class SharedPreferenceStorage(application: Application): PreferenceStorage {

    private val preferences: Lazy<SharedPreferences> = lazy {
        application.applicationContext.getSharedPreferences("CoinPreferences", Context.MODE_PRIVATE)
    }

    override var timeLoadedAtGlobal by LongPreferences(preferences, TIME_GLOBAL, 0)

    override var timeLoadedAtCurrency by LongPreferences(preferences, TIME_CURRENCY, 0)

    override var timeLoadedAtCategory by LongPreferences(preferences, TIME_CATEGORY, 0)

    override var timeLoadedAtExchange by LongPreferences(preferences, TIME_EXCHANGE, 0)

    override var timeLoadedAtDerivative by LongPreferences(preferences, TIME_DERIVATIVE, 0)

    override var currenciesSort by IntPreferences(preferences, CURRENCIES_SORT, 0)

    override var locale by StringPreferences(preferences, LOCALE_TO_SET, "ru")

    override var alertDialogLocale by IntPreferences(preferences, ALERT_DIALOG_LOCALE,1)

    override var alertDialogScreen by IntPreferences(preferences, ALERT_DIALOG_SCREEN, 0)

    override var notificationSwitch by BooleanPreferences(preferences, NOTIFICATION_SWITCH, false)
}

