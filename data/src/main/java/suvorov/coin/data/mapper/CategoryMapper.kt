package suvorov.coin.data.mapper

import suvorov.coin.data.remote.model.CategoryApi
import suvorov.coin.data.local.room.model.CategoryEntity
import suvorov.coin.domain.model.Category

object CategoryApiMapper: BaseMapper<CategoryApi, CategoryEntity> {
    override fun map(item: CategoryApi): CategoryEntity {
        return CategoryEntity(
            item.id ?: "",
            item.name ?: "",
            item.marketCap ?: 0.0,
            item.change ?: 0.0,
            item.top?.getOrNull(0) ?: "",
            item.top?.getOrNull(1) ?: "",
            item.top?.getOrNull(2) ?: "",
            item.volume ?: 0.0
        )
    }
}

object CategoryEntityMapper: BaseMapper<CategoryEntity, Category> {
    override fun map(item: CategoryEntity): Category {
        return Category(
            item.id,
            item.name,
            item.marketCap,
            item.change,
            item.firstTop,
            item.secondTop,
            item.thirdTop,
            item.volume
        )
    }
}