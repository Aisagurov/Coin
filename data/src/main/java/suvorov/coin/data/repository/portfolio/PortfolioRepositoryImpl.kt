package suvorov.coin.data.repository.portfolio

import kotlinx.coroutines.flow.map
import suvorov.coin.data.mapper.CurrencyEntityMapper
import suvorov.coin.domain.repository.PortfolioRepository

class PortfolioRepositoryImpl(
    private val localDataSource: PortfolioLocalDataSource
): PortfolioRepository {

    override val portfolioCurrencies =
        localDataSource.portfolioCurrencies.map { list ->
            list.filter { it.id.isEmpty().not() }
                .map { CurrencyEntityMapper.map(it) }
        }

    override suspend fun updatePortfolioStatus(id: String) =
        localDataSource.updatePortfolioStatus(id).let { item ->
            item?.let { CurrencyEntityMapper.map(it) }
        }
}