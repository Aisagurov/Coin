package suvorov.coin.domain.model

data class Exchange(
    val id: String,
    val name: String,
    val image: String,
    val trustScore: Int,
    val rank: Int,
    val volume: Double
)