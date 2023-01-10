package suvorov.coin.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exchange")
data class ExchangeEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val image: String,
    val trustScore: Int,
    val rank: Int,
    val volume: Double
)