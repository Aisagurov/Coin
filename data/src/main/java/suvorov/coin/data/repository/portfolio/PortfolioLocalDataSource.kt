package suvorov.coin.data.repository.portfolio

import suvorov.coin.data.local.room.CoinDao
import suvorov.coin.data.local.room.model.CurrencyEntity

class PortfolioLocalDataSource(private val dao: CoinDao) {

    val portfolioCurrencies = dao.portfolioList()

    suspend fun updatePortfolioStatus(id: String): CurrencyEntity? {
        dao.getCurrencyById(id)?.let {
            val currency = CurrencyEntity(
                it.id,
                it.symbol,
                it.name,
                it.image,
                it.price,
                it.cap,
                it.rank,
                it.priceChange,
                it.isPortfolio.not()
            )
            if (dao.updateCurrencies(currency) > 0) {
                return currency
            }
        }
        return null
    }
}