package suvorov.coin.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "derivative")
data class DerivativeEntity(
    @PrimaryKey
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