package suvorov.coin.data.repository.global

import suvorov.coin.data.local.room.CoinDao
import suvorov.coin.data.local.room.model.GlobalEntity

class GlobalLocalDataSource(private val dao: CoinDao) {

    val global = dao.global()

    suspend fun insertGlobalIntoDatabase(global: GlobalEntity) {
        dao.insertGlobal(global)
    }
}