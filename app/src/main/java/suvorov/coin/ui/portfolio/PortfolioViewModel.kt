package suvorov.coin.ui.portfolio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import suvorov.coin.domain.repository.PortfolioRepository

class PortfolioViewModel(private val repository: PortfolioRepository): ViewModel() {

    val portfolioCurrencies = repository.portfolioCurrencies.asLiveData()

    fun updatePortfolioStatus(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePortfolioStatus(id)
        }
    }
}