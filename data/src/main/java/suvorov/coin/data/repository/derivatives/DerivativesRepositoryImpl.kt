package suvorov.coin.data.repository.derivatives

import kotlinx.coroutines.flow.map
import suvorov.coin.data.local.preferences.PreferencesConstants.LOAD_MILLISECONDS
import suvorov.coin.data.local.preferences.PreferencesConstants.LOAD_SECONDS
import suvorov.coin.data.mapper.DerivativeApiMapper
import suvorov.coin.data.mapper.DerivativeEntityMapper
import suvorov.coin.domain.repository.DerivativesRepository
import suvorov.coin.domain.repository.PreferenceStorage
import java.util.*

class DerivativesRepositoryImpl(
    private val remoteDataSource: DerivativesRemoteDataSource,
    private val localDataSource: DerivativesLocalDataSource,
    private val preferenceStorage: PreferenceStorage
): DerivativesRepository {

    override val derivativesListFromDatabase =
        localDataSource.derivativesList.map { list ->
            list.filter { it.id.isEmpty().not() }
                .map { DerivativeEntityMapper.map(it) }
        }

    override suspend fun getDerivativesFromApi(count: Int) {
        val customList = remoteDataSource.getDerivatives(count).let { list ->
            list.filter { it.id.isNullOrEmpty().not() }
                .map { DerivativeApiMapper.map(it) }
        }
        localDataSource.insertDerivativesIntoDatabase(customList)
        preferenceStorage.timeLoadedAtDerivative = Date().time
    }

    override fun loadData(): Boolean {
        val lastLoadedTime = preferenceStorage.timeLoadedAtDerivative
        val currentTime = Date().time
        return currentTime - lastLoadedTime > LOAD_SECONDS * LOAD_MILLISECONDS
    }
}