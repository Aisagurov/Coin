package suvorov.coin.presentation.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import suvorov.coin.data.remote.HistoricalPrice
import suvorov.coin.domain.interactor.HistoryInteractor

class HistoryViewModel(private val interactor: HistoryInteractor): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun coinFromSymbol(symbol: String) = interactor.coinFromSymbol(symbol)

    suspend fun getHistoricalPrice(id: String): HistoricalPrice {
        var result = HistoricalPrice(listOf())
        _isLoading.postValue(true)
        kotlin.runCatching {
            result = interactor.getHistoricalPrice(id)
        }.onSuccess {
            _isLoading.postValue(false)
        }
        return result
    }
}