package suvorov.coin.data.repository.exchange

import suvorov.coin.data.local.room.CoinDao

class ExchangeLocalDataSource(private val dao: CoinDao) {
    fun exchangeById(id: String) = dao.exchangeById(id)
}