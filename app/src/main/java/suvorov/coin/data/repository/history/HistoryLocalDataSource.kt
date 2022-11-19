package suvorov.coin.data.repository.history

import suvorov.coin.data.local.database.CoinDao

class HistoryLocalDataSource(private val dao: CoinDao) {
    fun coinFromSymbol(symbol: String) = dao.coinLiveDataFromSymbol(symbol)
}