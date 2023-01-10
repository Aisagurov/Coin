package suvorov.coin.data.repository.derivatives

import suvorov.coin.data.local.room.CoinDao
import suvorov.coin.data.local.room.model.DerivativeEntity

class DerivativesLocalDataSource(private val dao: CoinDao) {

    val derivativesList = dao.derivativesList()

    suspend fun insertDerivativesIntoDatabase(list: List<DerivativeEntity>) {
        if (list.isNotEmpty()) {
            dao.insertDerivatives(list)
        }
    }
}