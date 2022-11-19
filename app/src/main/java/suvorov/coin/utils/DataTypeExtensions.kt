package suvorov.coin.utils

import java.text.DecimalFormat

fun String?.emptyIfNull(): String {
    return this ?: ""
}

fun Double?.dollarString(): String {
    return this?.let {
        val numberFormat = DecimalFormat("#,##0.00")
        "$ ${numberFormat.format(this)}"
    } ?: ""
}