package suvorov.coin.utils

object PriceHelper {
    fun showValuePrice(value: Double?): String {
        return "%.2f".format(value)
    }
}