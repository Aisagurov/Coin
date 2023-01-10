package suvorov.coin.ui.exchanges.adapter

import android.annotation.SuppressLint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import suvorov.coin.R
import suvorov.coin.databinding.ExchangesItemBinding
import suvorov.coin.domain.model.Exchange
import suvorov.coin.ui.exchanges.adapter.clicklistener.ExchangesClickListener
import suvorov.coin.util.btcString
import suvorov.coin.util.load

class ExchangesViewHolder(
    private val binding: ExchangesItemBinding
): RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind(model: Exchange, clickListener: ExchangesClickListener) {
        val context = binding.root.context
        binding.apply {
            rankTextView.text = model.rank.toString()
            imageView.load(model.image)
            nameTextView.text = model.name
            volumeTextView.text = model.volume.btcString()
            trustScoreTextView.text = "${model.trustScore}${context.getString(R.string.ten)}"
            trustScoreCardView.setCardBackgroundColor(
                when(model.trustScore) {
                    in 0..3 -> ContextCompat.getColor(context, R.color.red)
                    in 4..8 -> ContextCompat.getColor(context, R.color.yellow)
                    else -> ContextCompat.getColor(context, R.color.green)
                }
            )
            root.setOnClickListener {
                clickListener.onExchangeClick(model.id)
            }
        }
    }
}