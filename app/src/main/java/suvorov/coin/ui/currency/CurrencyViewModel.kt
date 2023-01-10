package suvorov.coin.ui.currency

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import suvorov.coin.domain.repository.CurrencyRepository

class CurrencyViewModel(private val repository: CurrencyRepository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _dataError = MutableLiveData<Boolean>()
    val dataError: LiveData<Boolean> = _dataError

    private val _currencyHistory = MutableLiveData<List<DoubleArray>>()
    val currencyHistory: LiveData<List<DoubleArray>> = _currencyHistory

    fun currencyById(id: String) = repository.currencyById(id).asLiveData()

    fun getCurrencyHistory(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            kotlin.runCatching {
                repository.getCurrencyHistory(id)
            }.onSuccess {
                _isLoading.postValue(false)
                _dataError.postValue(false)
                _currencyHistory.postValue(it)
            }.onFailure {
                _isLoading.postValue(false)
                _dataError.postValue(true)
            }
        }
    }

    fun updatePortfolioStatus(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePortfolioStatus(id)
        }
    }
}