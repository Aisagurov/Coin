package suvorov.coin.domain.model

data class Currency(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val price: Double,
    val cap: Long,
    val rank: Int,
    val priceChange: Double,
    val isPortfolio: Boolean
)