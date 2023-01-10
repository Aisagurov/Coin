package suvorov.coin.data.remote.model

import com.google.gson.annotations.SerializedName

data class ExchangeApi(
    val id: String?,
    val name: String?,
    val image: String?,
    @SerializedName("trust_score") val trustScore: Int?,
    @SerializedName("trust_score_rank") val rank: Int?,
    @SerializedName("trade_volume_24h_btc") val volume: Double?
)