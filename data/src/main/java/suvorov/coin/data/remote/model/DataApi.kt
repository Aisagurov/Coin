package suvorov.coin.data.remote.model

import com.google.gson.annotations.SerializedName

data class DataApi(
    @SerializedName("defi_market_cap") val cap: Double?,
    @SerializedName("trading_volume_24h") val volume: Double?,
    @SerializedName("defi_dominance") val dominance: Double?
)