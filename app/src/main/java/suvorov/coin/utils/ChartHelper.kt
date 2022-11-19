package suvorov.coin.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

object ChartHelper {
    @SuppressLint("SimpleDateFormat")
    fun getDate(value: Double): String {
        return SimpleDateFormat("dd.MMM").format(value)
    }
}