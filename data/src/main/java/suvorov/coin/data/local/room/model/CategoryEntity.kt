package suvorov.coin.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val marketCap: Double,
    val change: Double,
    val firstTop: String,
    val secondTop: String,
    val thirdTop: String,
    val volume: Double
)