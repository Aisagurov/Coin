package suvorov.coin.data.mapper

import suvorov.coin.data.remote.model.ExchangeApi
import suvorov.coin.data.local.room.model.ExchangeEntity
import suvorov.coin.domain.model.Exchange

object ExchangeApiMapper: BaseMapper<ExchangeApi, ExchangeEntity> {
    override fun map(item: ExchangeApi): ExchangeEntity {
        return ExchangeEntity(
            item.id ?: "",
            item.name ?: "",
            item.image ?: "",
            item.trustScore ?: 0,
            item.rank ?: 0,
            item.volume ?: 0.0
        )
    }
}

object ExchangeEntityMapper: BaseMapper<ExchangeEntity, Exchange> {
    override fun map(item: ExchangeEntity): Exchange {
        return Exchange(
            item.id,
            item.name,
            item.image,
            item.trustScore,
            item.rank,
            item.volume
        )
    }
}