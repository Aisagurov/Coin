package suvorov.coin.data.repository.exchange

import kotlinx.coroutines.flow.map
import suvorov.coin.data.mapper.ExchangeEntityMapper
import suvorov.coin.domain.repository.ExchangeRepository

class ExchangeRepositoryImpl(
    private val remoteDataSource: ExchangeRemoteDataSource,
    private val localDataSource: ExchangeLocalDataSource
): ExchangeRepository {

    override fun exchangeById(id: String) =
        localDataSource.exchangeById(id).map { ExchangeEntityMapper.map(it) }

    override suspend fun getExchangeHistory(id: String, days: Int) =
        remoteDataSource.getExchangeHistory(id, days)
}