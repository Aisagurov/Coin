package suvorov.coin.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.gson.*
import suvorov.coin.data.remote.model.*

class ApiService(private val baseUrl: String = "https://api.coingecko.com/api/v3") {

    private val client = HttpClient { install(ContentNegotiation) { gson() } }

    suspend fun getGlobal() =
        client.get("$baseUrl/global/decentralized_finance_defi").body<GlobalApi>()


    suspend fun getCurrencies(currency: String, count: Int) =
        client.get("$baseUrl/coins/markets") {
            url {
                parameter("vs_currency", currency)
                parameter("per_page", count)
            }
        }.body<List<CurrencyApi>>()


    suspend fun getCategories() =
        client.get("$baseUrl/coins/categories").body<List<CategoryApi>>()


    suspend fun getExchanges(count: Int) =
        client.get("$baseUrl/exchanges"){
            url {
                parameter("per_page", count)
            }
        }.body<List<ExchangeApi>>()


    suspend fun getDerivatives(count: Int) =
        client.get("$baseUrl/derivatives/exchanges"){
            url {
                parameter("per_page", count)
            }
        }.body<List<DerivativeApi>>()


    suspend fun getCurrencyHistory(id: String, currency: String, days: Int) =
        client.get("$baseUrl/coins/${id}/market_chart") {
            url {
                parameter("vs_currency", currency)
                parameter("days", days)
            }
        }.body<CoinPriceApi>()


    suspend fun getExchangeHistory(id: String, days: Int) =
        client.get("$baseUrl/exchanges/${id}/volume_chart") {
            url {
                parameter("days", days)
            }
        }.body<List<DoubleArray>>()
}