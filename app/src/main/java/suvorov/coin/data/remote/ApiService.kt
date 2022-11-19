package suvorov.coin.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class ApiService(
    private val client: HttpClient,
    private val baseUrl: String = "https://api.coingecko.com/api/v3"
) {
    suspend fun getCoinsList(coin: String) =
        client.get("$baseUrl/coins/markets") {
            url {
                parameter("vs_currency", coin)
            }
        }.body<List<Coin>>()

    suspend fun getHistoricalPrice(id: String, coin: String, days: Int) =
        client.get("$baseUrl/coins/${id}/market_chart") {
            url {
                parameter("vs_currency", coin)
                parameter("days", days)
            }
        }.body<HistoricalPrice>()
}