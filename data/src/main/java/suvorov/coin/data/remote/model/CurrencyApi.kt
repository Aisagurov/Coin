package suvorov.coin.data.remote.model

import com.google.gson.annotations.SerializedName

data class CurrencyApi(
    val id: String?,
    val symbol: String?,
    val name: String?,
    val image: String?,
    @SerializedName("current_price") val price: Double?,
    @SerializedName("market_cap") val cap: Long?,
    @SerializedName("market_cap_rank") val rank: Int?,
    @SerializedName("price_change_percentage_24h") val priceChange: Double?
)