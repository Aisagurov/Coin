package suvorov.coin.data.repository.global

import kotlinx.coroutines.flow.map
import suvorov.coin.data.local.preferences.PreferencesConstants.LOAD_MILLISECONDS
import suvorov.coin.data.local.preferences.PreferencesConstants.LOAD_SECONDS
import suvorov.coin.data.mapper.GlobalApiMapper
import suvorov.coin.data.mapper.GlobalEntityMapper
import suvorov.coin.domain.repository.GlobalRepository
import suvorov.coin.domain.repository.PreferenceStorage
import java.util.*

class GlobalRepositoryImpl(
    private val remoteDataSource: GlobalRemoteDataSource,
    private val localDataSource: GlobalLocalDataSource,
    private val preferenceStorage: PreferenceStorage
): GlobalRepository {

    override val globalFromDatabase = localDataSource.global.map { GlobalEntityMapper.map(it) }

    override suspend fun getGlobalFromApi() {
        val global = GlobalApiMapper.map(remoteDataSource.getGlobal())
        localDataSource.insertGlobalIntoDatabase(global)
        preferenceStorage.timeLoadedAtGlobal = Date().time
    }

    override fun loadData(): Boolean {
        val lastLoadedTime = preferenceStorage.timeLoadedAtGlobal
        val currentTime = Date().time
        return currentTime - lastLoadedTime > LOAD_SECONDS * LOAD_MILLISECONDS
    }
}