package suvorov.coin.data.repository.derivative

import kotlinx.coroutines.flow.map
import suvorov.coin.data.mapper.DerivativeEntityMapper
import suvorov.coin.domain.repository.DerivativeRepository

class DerivativeRepositoryImpl(
    private val localDataSource: DerivativeLocalDataSource
): DerivativeRepository {
    override fun derivativeByName(name: String) =
        localDataSource.derivativeByName(name).map { DerivativeEntityMapper.map(it) }
}