package suvorov.coin.ui.exchange

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import suvorov.coin.domain.repository.ExchangeRepository

class ExchangeViewModel(private val repository: ExchangeRepository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _dataError = MutableLiveData<Boolean>()
    val dataError: LiveData<Boolean> = _dataError

    private val _exchangeHistory = MutableLiveData<List<DoubleArray>>()
    val exchangeHistory: LiveData<List<DoubleArray>> = _exchangeHistory

    fun exchangeById(id: String) = repository.exchangeById(id).asLiveData()

    fun getExchangeHistory(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            kotlin.runCatching {
                repository.getExchangeHistory(id)
            }.onSuccess {
                _isLoading.postValue(false)
                _dataError.postValue(false)
                _exchangeHistory.postValue(it)
            }.onFailure {
                _isLoading.postValue(false)
                _dataError.postValue(true)
            }
        }
    }
}