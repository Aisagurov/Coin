package suvorov.coin.ui.categories

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import suvorov.coin.domain.repository.CategoriesRepository

class CategoriesViewModel(private val repository: CategoriesRepository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val categoriesListFromDatabase = repository.categoriesListFromDatabase.asLiveData()

    fun isListEmpty(): Boolean {
        return categoriesListFromDatabase.value?.isEmpty() ?: true
    }

    fun getCategoriesFromApi() {
        if (repository.loadData()) {
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                kotlin.runCatching {
                    repository.getCategoriesFromApi()
                }.onSuccess {
                    _isLoading.postValue(false)
                }.onFailure {
                    _isLoading.postValue(false)
                }
            }
        }
    }
}