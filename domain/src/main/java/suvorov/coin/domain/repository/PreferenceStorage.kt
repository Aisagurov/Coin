package suvorov.coin.domain.repository

interface PreferenceStorage {
    var timeLoadedAtGlobal: Long
    var timeLoadedAtCurrency: Long
    var timeLoadedAtCategory: Long
    var timeLoadedAtExchange: Long
    var timeLoadedAtDerivative: Long
    var currenciesSort: Int
    var locale: String?
    var alertDialogLocale: Int
    var alertDialogScreen: Int
    var notificationSwitch: Boolean
}