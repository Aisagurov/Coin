package suvorov.coin.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "global")
data class GlobalEntity(
    @PrimaryKey
    val cap: Double,
    val volume: Double,
    val dominance: Double
)