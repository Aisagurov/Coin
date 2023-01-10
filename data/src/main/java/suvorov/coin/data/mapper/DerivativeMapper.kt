package suvorov.coin.data.mapper

import suvorov.coin.data.remote.model.DerivativeApi
import suvorov.coin.data.local.room.model.DerivativeEntity
import suvorov.coin.domain.model.Derivative

object DerivativeApiMapper: BaseMapper<DerivativeApi, DerivativeEntity> {
    override fun map(item: DerivativeApi): DerivativeEntity {
        return DerivativeEntity(
            item.name ?: "",
            item.id ?: "",
            item.interest ?: 0.0,
            item.volume ?: "",
            item.numberPerpetual ?: 0,
            item.numberFutures ?: 0,
            item.year ?: 0,
            item.url ?: "",
            item.image ?: ""
        )
    }
}

object DerivativeEntityMapper: BaseMapper<DerivativeEntity, Derivative> {
    override fun map(item: DerivativeEntity): Derivative {
        return Derivative(
            item.name,
            item.id,
            item.interest,
            item.volume,
            item.numberPerpetual,
            item.numberFutures,
            item.year,
            item.url,
            item.image
        )
    }
}