package suvorov.coin.domain.repository

import kotlinx.coroutines.flow.Flow
import suvorov.coin.domain.model.Global

interface GlobalRepository {
    val globalFromDatabase: Flow<Global>
    suspend fun getGlobalFromApi()
    fun loadData(): Boolean
}