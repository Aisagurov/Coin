package suvorov.coin.data.repository.derivative

import suvorov.coin.data.local.room.CoinDao

class DerivativeLocalDataSource(private val dao: CoinDao) {
    fun derivativeByName(name: String) = dao.derivativeByName(name)
}