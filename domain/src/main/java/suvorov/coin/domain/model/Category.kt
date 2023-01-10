package suvorov.coin.domain.model

data class Category(
    val id: String,
    val name: String,
    val marketCap: Double,
    val change: Double,
    val firstTop: String,
    val secondTop: String,
    val thirdTop: String,
    val volume: Double
)
