package suvorov.coin.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency")
data class CurrencyEntity(
    @PrimaryKey
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val price: Double,
    val cap: Long,
    val rank: Int,
    val priceChange: Double,
    val isPortfolio: Boolean = false
)