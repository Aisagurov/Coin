package suvorov.coin.data.mapper

import suvorov.coin.data.remote.model.CoinPriceApi

object CoinPriceApiMapper: BaseMapper<CoinPriceApi, List<DoubleArray>> {
    override fun map(item: CoinPriceApi): List<DoubleArray> {
        return item.prices
    }
}