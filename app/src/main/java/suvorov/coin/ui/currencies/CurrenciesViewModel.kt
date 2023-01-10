package suvorov.coin.ui.currencies

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import suvorov.coin.domain.repository.CurrenciesRepository
import suvorov.coin.domain.repository.GlobalRepository

class CurrenciesViewModel(
    private val currenciesRepository: CurrenciesRepository,
    private val globalRepository: GlobalRepository
): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val currenciesListFromDatabase = currenciesRepository.currenciesListFromDatabase.asLiveData()

    val globalFromDatabase = globalRepository.globalFromDatabase.asLiveData()

    fun isListEmpty(): Boolean {
        return currenciesListFromDatabase.value?.isEmpty() ?: true
    }

    fun getCurrenciesFromApi() {
        if (currenciesRepository.loadData()) {
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                kotlin.runCatching {
                    currenciesRepository.getCurrenciesFromApi()
                }.onSuccess {
                    _isLoading.postValue(false)
                }.onFailure {
                    _isLoading.postValue(false)
                }
            }
        }
    }

    fun getGlobalFromApi() {
        viewModelScope.launch(Dispatchers.IO) {
            if (globalRepository.loadData()) {
                kotlin.runCatching {
                    globalRepository.getGlobalFromApi()
                }
            }
        }
    }

    fun updatePortfolioStatus(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            currenciesRepository.updatePortfolioStatus(id)
        }
    }
}