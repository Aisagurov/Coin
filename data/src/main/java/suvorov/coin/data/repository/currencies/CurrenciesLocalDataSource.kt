package suvorov.coin.data.repository.currencies

import suvorov.coin.data.local.room.CoinDao
import suvorov.coin.data.local.room.model.CurrencyEntity

class CurrenciesLocalDataSource(private val dao: CoinDao) {

    val currenciesList = dao.currenciesList()

    suspend fun insertCurrenciesIntoDatabase(list: List<CurrencyEntity>) {
        if (list.isNotEmpty()) {
            dao.insertCurrencies(list)
        }
    }

    suspend fun portfolioIds() = dao.portfolioIds()

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