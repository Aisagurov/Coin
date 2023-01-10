package suvorov.coin.data.repository.currency

import kotlinx.coroutines.flow.map
import suvorov.coin.data.mapper.CoinPriceApiMapper
import suvorov.coin.data.mapper.CurrencyEntityMapper
import suvorov.coin.domain.repository.CurrencyRepository

class CurrencyRepositoryImpl(
    private val remoteDataSource: CurrencyRemoteDataSource,
    private val localDataSource: CurrencyLocalDataSource
): CurrencyRepository {

    override fun currencyById(id: String) =
        localDataSource.currencyById(id).map {
            CurrencyEntityMapper.map(it)
        }

    override suspend fun getCurrencyHistory(id: String, currency: String, days: Int) =
        remoteDataSource.getCurrencyHistory(id, currency, days).let {
            CoinPriceApiMapper.map(it)
        }

    override suspend fun updatePortfolioStatus(id: String) =
        localDataSource.updatePortfolioStatus(id).let { item ->
            item?.let { CurrencyEntityMapper.map(it) }
        }
}