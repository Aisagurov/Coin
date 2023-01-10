package suvorov.coin.data.remote.model

import com.google.gson.annotations.SerializedName

data class DerivativeApi(
    val name: String?,
    val id: String?,
    @SerializedName("open_interest_btc") val interest: Double?,
    @SerializedName("trade_volume_24h_btc") val volume: String?,
    @SerializedName("number_of_perpetual_pairs") val numberPerpetual: Int?,
    @SerializedName("number_of_futures_pairs") val numberFutures: Int?,
    @SerializedName("year_established") val year: Int?,
    val url: String?,
    val image: String?
)
