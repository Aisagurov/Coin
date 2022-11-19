package suvorov.coin.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin")
data class CoinEntity(
    @PrimaryKey
    val symbol: String,
    val id: String?,
    val name: String?,
    val image: String?,
    val price: Double?,
    val changePrice: Double?,
    val isFavorite: Boolean = false
)