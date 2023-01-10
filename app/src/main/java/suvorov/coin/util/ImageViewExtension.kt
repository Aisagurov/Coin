package suvorov.coin.util

import android.widget.ImageView
import coil.load
import suvorov.coin.R

fun ImageView.load(url: String) {
    this.load(url) {
        placeholder(R.drawable.place_holder_image)
        error(R.drawable.no_image)
    }
}