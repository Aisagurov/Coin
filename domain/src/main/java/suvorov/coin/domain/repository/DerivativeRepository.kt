package suvorov.coin.domain.repository

import kotlinx.coroutines.flow.Flow
import suvorov.coin.domain.model.Derivative

interface DerivativeRepository {
    fun derivativeByName(name: String): Flow<Derivative>
}