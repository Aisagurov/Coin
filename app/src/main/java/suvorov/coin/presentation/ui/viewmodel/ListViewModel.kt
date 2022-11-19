package suvorov.coin.presentation.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import suvorov.coin.domain.interactor.ListInteractor

class ListViewModel(private val interactor: ListInteractor): ViewModel() {

    val coinsListFromDatabase = interactor.coinsListFromDatabase

    val favoritesListFromDatabase = interactor.favoritesListFromDatabase

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun isListEmpty(): Boolean {
        return coinsListFromDatabase.value?.isEmpty() ?: true
    }

    fun getCoinsList(coin: String = "usd") {
        if (interactor.loadData()) {
            viewModelScope.launch {
                _isLoading.postValue(true)
                kotlin.runCatching {
                    interactor.getCoinsList(coin)
                }.onSuccess {
                    _isLoading.postValue(false)
                }
            }
        }
    }

    fun updateFavoritesStatus(symbol: String) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.updateFavoritesStatus(symbol)
        }
    }
}