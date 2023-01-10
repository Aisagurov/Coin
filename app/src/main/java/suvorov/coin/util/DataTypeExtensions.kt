package suvorov.coin.util

import java.text.DecimalFormat

fun Double?.priceDollarString(): String {
    return this?.let {
        val numberFormat = DecimalFormat("#,##0.00")
        "${numberFormat.format(this)} $"
    } ?: ""
}

fun Double?.dollarString(): String {
    return this?.let {
        val numberFormat = DecimalFormat("###,###,###,###,###")
        "${numberFormat.format(this).replace(",", " ")} $"
    } ?: ""
}

fun Long?.dollarString(): String {
    return this?.let {
        val numberFormat = DecimalFormat("###,###,###,###,###")
        "${numberFormat.format(this).replace(",", " ")} $"
    } ?: ""
}

fun Double?.percentString(): String {
    return this?.let {
        val numberFormat = DecimalFormat("#.#")
        "${numberFormat.format(this)} %"
    } ?: ""
}

fun Double?.btcString(): String {
    return this?.let {
        val numberFormat = DecimalFormat("###,###,###,###,###")
        "${numberFormat.format(this).replace(",", " ")} BTC"
    } ?: ""
}