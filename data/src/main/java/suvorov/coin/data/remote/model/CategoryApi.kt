package suvorov.coin.data.remote.model

import com.google.gson.annotations.SerializedName

data class CategoryApi(
    val id: String?,
    val name: String?,
    @SerializedName("market_cap") val marketCap: Double?,
    @SerializedName("market_cap_change_24h") val change: Double?,
    @SerializedName("top_3_coins") val top: List<String>?,
    @SerializedName("volume_24h") val volume: Double?
)