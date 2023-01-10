package suvorov.coin.data.repository.exchanges

import suvorov.coin.data.local.room.CoinDao
import suvorov.coin.data.local.room.model.ExchangeEntity

class ExchangesLocalDataSource(private val dao: CoinDao) {

    val exchangesList = dao.exchangesList()

    suspend fun insertExchangesIntoDatabase(list: List<ExchangeEntity>) {
        if (list.isNotEmpty()) {
            dao.insertExchanges(list)
        }
    }
}