package suvorov.coin.ui.derivative

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import suvorov.coin.domain.repository.DerivativeRepository

class DerivativeViewModel(private val repository: DerivativeRepository): ViewModel() {
    fun derivativeByName(name: String) = repository.derivativeByName(name).asLiveData()
}