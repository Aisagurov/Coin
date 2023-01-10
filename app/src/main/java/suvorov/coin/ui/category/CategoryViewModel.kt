package suvorov.coin.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import suvorov.coin.domain.repository.CategoryRepository

class CategoryViewModel(private val repository: CategoryRepository): ViewModel() {
    fun categoryById(id: String) = repository.categoryById(id).asLiveData()
}