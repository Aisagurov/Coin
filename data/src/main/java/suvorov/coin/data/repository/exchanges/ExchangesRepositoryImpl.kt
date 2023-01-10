package suvorov.coin.data.repository.exchanges

import kotlinx.coroutines.flow.map
import suvorov.coin.data.local.preferences.PreferencesConstants.LOAD_MILLISECONDS
import suvorov.coin.data.local.preferences.PreferencesConstants.LOAD_SECONDS
import suvorov.coin.data.mapper.ExchangeApiMapper
import suvorov.coin.data.mapper.ExchangeEntityMapper
import suvorov.coin.domain.repository.ExchangesRepository
import suvorov.coin.domain.repository.PreferenceStorage
import java.util.*

class ExchangesRepositoryImpl(
    private val remoteDataSource: ExchangesRemoteDataSource,
    private val localDataSource: ExchangesLocalDataSource,
    private val preferenceStorage: PreferenceStorage
): ExchangesRepository {

    override val exchangesListFromDatabase =
        localDataSource.exchangesList.map { list ->
            list.filter { it.id.isEmpty().not() }
                .map { ExchangeEntityMapper.map(it) }
        }

    override suspend fun getExchangesFromApi(count: Int) {
        val customList = remoteDataSource.getExchanges(count).let { list ->
            list.filter { it.id.isNullOrEmpty().not() }
                .map { ExchangeApiMapper.map(it) }
        }
        localDataSource.insertExchangesIntoDatabase(customList)
        preferenceStorage.timeLoadedAtExchange = Date().time
    }

    override fun loadData(): Boolean {
        val lastLoadedTime = preferenceStorage.timeLoadedAtExchange
        val currentTime = Date().time
        return currentTime - lastLoadedTime > LOAD_SECONDS * LOAD_MILLISECONDS
    }
}