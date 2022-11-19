package suvorov.coin.data.repository.history

import suvorov.coin.domain.repository.HistoryRepository

class HistoryRepositoryImpl(
    private val localDataSource: HistoryLocalDataSource,
    private val remoteDataSource: HistoryRemoteDataSource
    ): HistoryRepository {

    override fun coinFromSymbol(symbol: String) = localDataSource.coinFromSymbol(symbol)

    override suspend fun getHistoricalPrice(id: String, coin: String) =
        remoteDataSource.getHistoricalPrice(id, coin)
}