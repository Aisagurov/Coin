package suvorov.coin.util

import android.widget.TextView
import androidx.core.content.ContextCompat
import suvorov.coin.R

object PriceHelper {
    fun showPriceChange(textView: TextView, change: Double?) {
        val priceChange = "%.1f".format(change) + "%"
        textView.text = priceChange

        val context = textView.context

        if (priceChange.contains("-")) {
            textView.setTextColor(ContextCompat.getColor(context, R.color.red))
            textView.setCompoundDrawablesWithIntrinsicBounds(null, null,
                ContextCompat.getDrawable(context, R.drawable.arrow_drop_down_icon), null)
        } else {
            textView.setTextColor(ContextCompat.getColor(context, R.color.green))
            textView.setCompoundDrawablesWithIntrinsicBounds(null, null,
                ContextCompat.getDrawable(context, R.drawable.arrow_drop_up_icon), null)
        }
    }
}