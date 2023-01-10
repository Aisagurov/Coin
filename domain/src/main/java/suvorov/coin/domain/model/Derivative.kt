package suvorov.coin.domain.model

data class Derivative(
    val name: String,
    val id: String,
    val interest: Double,
    val volume: String,
    val numberPerpetual: Int,
    val numberFutures: Int,
    val year: Int,
    val url: String,
    val image: String
)
