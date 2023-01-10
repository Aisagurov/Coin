package suvorov.coin.data.repository.currency

import suvorov.coin.data.local.room.CoinDao
import suvorov.coin.data.local.room.model.CurrencyEntity

class CurrencyLocalDataSource(private val dao: CoinDao) {

    fun currencyById(id: String) = dao.currencyById(id)

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