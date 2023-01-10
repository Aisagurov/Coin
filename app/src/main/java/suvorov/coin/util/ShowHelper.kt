package suvorov.coin.util

import android.content.Context
import android.widget.Toast

object ShowHelper {
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}