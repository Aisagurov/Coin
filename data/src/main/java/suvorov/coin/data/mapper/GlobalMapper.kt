package suvorov.coin.data.mapper

import suvorov.coin.data.remote.model.GlobalApi
import suvorov.coin.data.local.room.model.GlobalEntity
import suvorov.coin.domain.model.Global

object GlobalApiMapper: BaseMapper<GlobalApi, GlobalEntity> {
    override fun map(item: GlobalApi): GlobalEntity {
        return GlobalEntity(
            item.data.cap ?: 0.0,
            item.data.volume ?: 0.0,
            item.data.dominance ?: 0.0
        )
    }
}

object GlobalEntityMapper {
    fun map(item: GlobalEntity?): Global {
        return Global(
            item?.cap ?: 0.0,
            item?.volume ?: 0.0,
            item?.dominance ?: 0.0
        )
    }
}