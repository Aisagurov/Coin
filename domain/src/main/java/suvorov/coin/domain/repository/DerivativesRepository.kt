package suvorov.coin.domain.repository

import kotlinx.coroutines.flow.Flow
import suvorov.coin.domain.model.Derivative

interface DerivativesRepository {
    val derivativesListFromDatabase: Flow<List<Derivative>>
    suspend fun getDerivativesFromApi(count: Int)
    fun loadData(): Boolean
}