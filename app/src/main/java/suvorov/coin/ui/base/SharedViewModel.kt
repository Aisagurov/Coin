package suvorov.coin.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    private val _currenciesSort = MutableLiveData<String>()
    val currenciesSort: LiveData<String> = _currenciesSort

    private val _notificationSwitch = MutableLiveData<Boolean>()
    val notificationSwitch: LiveData<Boolean> = _notificationSwitch

    fun saveCurrenciesSort(value: String) {
        _currenciesSort.value = value
    }

    fun saveNotificationSwitch(value: Boolean) {
        _notificationSwitch.value = value
    }
}