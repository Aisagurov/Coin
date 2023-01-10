package suvorov.coin.ui.derivatives

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import suvorov.coin.domain.repository.DerivativesRepository

class DerivativesViewModel(private val repository: DerivativesRepository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val derivativesListFromDatabase = repository.derivativesListFromDatabase.asLiveData()

    fun isListEmpty(): Boolean {
        return derivativesListFromDatabase.value?.isEmpty() ?: true
    }

    fun getDerivativesFromApi(count: Int = 50) {
        if (repository.loadData()) {
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                kotlin.runCatching {
                    repository.getDerivativesFromApi(count)
                }.onSuccess {
                    _isLoading.postValue(false)
                }.onFailure {
                    _isLoading.postValue(false)
                }
            }
        }
    }
}