package suvorov.coin.data.mapper

import suvorov.coin.data.remote.model.CurrencyApi
import suvorov.coin.data.local.room.model.CurrencyEntity
import suvorov.coin.domain.model.Currency

object CurrencyApiMapper {
    fun map(item: CurrencyApi, list: List<String>): CurrencyEntity {
        return CurrencyEntity(
            item.id ?: "",
            item.symbol ?: "",
            item.name ?: "",
            item.image ?: "",
            item.price ?: 0.0,
            item.cap ?: 0,
            item.rank ?: 0,
            item.priceChange ?: 0.0,
            list.contains(item.id)
        )
    }
}

object CurrencyEntityMapper: BaseMapper<CurrencyEntity, Currency> {
    override fun map(item: CurrencyEntity): Currency {
        return Currency(
            item.id,
            item.symbol,
            item.name,
            item.image,
            item.price,
            item.cap,
            item.rank,
            item.priceChange,
            item.isPortfolio
        )
    }
}