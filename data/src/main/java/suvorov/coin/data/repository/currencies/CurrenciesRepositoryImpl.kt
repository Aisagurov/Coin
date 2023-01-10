package suvorov.coin.data.repository.currencies

import kotlinx.coroutines.flow.map
import suvorov.coin.data.local.preferences.PreferencesConstants.LOAD_MILLISECONDS
import suvorov.coin.data.local.preferences.PreferencesConstants.LOAD_SECONDS
import suvorov.coin.data.mapper.CurrencyApiMapper
import suvorov.coin.data.mapper.CurrencyEntityMapper
import suvorov.coin.domain.repository.CurrenciesRepository
import suvorov.coin.domain.repository.PreferenceStorage
import java.util.*

class CurrenciesRepositoryImpl(
    private val remoteDataSource: CurrenciesRemoteDataSource,
    private val localDataSource: CurrenciesLocalDataSource,
    private val preferenceStorage: PreferenceStorage
): CurrenciesRepository {

    override val currenciesListFromDatabase =
        localDataSource.currenciesList.map { list ->
            list.filter { it.id.isEmpty().not() }
                .map { CurrencyEntityMapper.map(it) }
        }

    override suspend fun getCurrenciesFromApi(currency: String, count: Int) {
        val portfolioIds = localDataSource.portfolioIds()
        val coinsList = remoteDataSource.getCurrencies(currency, count).let { list ->
            list.filter { it.id.isNullOrEmpty().not() }
                .map { CurrencyApiMapper.map(it, portfolioIds) }
        }
        localDataSource.insertCurrenciesIntoDatabase(coinsList)
        preferenceStorage.timeLoadedAtCurrency = Date().time
    }

    override suspend fun updatePortfolioStatus(id: String) =
        localDataSource.updatePortfolioStatus(id).let { item ->
            item?.let { CurrencyEntityMapper.map(it) }
        }

    override fun loadData(): Boolean {
        val lastLoadedTime = preferenceStorage.timeLoadedAtCurrency
        val currentTime = Date().time
        return currentTime - lastLoadedTime > LOAD_SECONDS * LOAD_MILLISECONDS
    }
}