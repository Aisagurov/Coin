package suvorov.coin.ui.exchanges

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import suvorov.coin.domain.repository.ExchangesRepository

class ExchangesViewModel(private val repository: ExchangesRepository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val exchangesListFromDatabase = repository.exchangesListFromDatabase.asLiveData()

    fun isListEmpty(): Boolean {
        return exchangesListFromDatabase.value?.isEmpty() ?: true
    }

    fun getExchangesFromApi(count: Int = 50) {
        if (repository.loadData()) {
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                kotlin.runCatching {
                    repository.getExchangesFromApi(count)
                }.onSuccess {
                    _isLoading.postValue(false)
                }.onFailure {
                    _isLoading.postValue(false)
                }
            }
        }
    }
}