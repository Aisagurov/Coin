package suvorov.coin.ui.currencies.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import suvorov.coin.databinding.CurrenciesItemBinding
import suvorov.coin.domain.model.Currency
import suvorov.coin.ui.currencies.adapter.clicklistener.CurrenciesClickListener
import suvorov.coin.util.*

class CurrenciesViewHolder(
    private val binding: CurrenciesItemBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(model: Currency, clickListener: CurrenciesClickListener) {
        binding.apply {
            rankTextView.text = model.rank.toString()
            imageView.load(model.image)
            symbolTextView.text = model.symbol.uppercase()
            priceTextView.text = model.price.priceDollarString()
            PriceHelper.showPriceChange(priceChangeTextView, model.priceChange)
            capTextView.text = model.cap.dollarString()
            portfolioImageView.visibility = if(model.isPortfolio) View.VISIBLE else View.GONE
            root.setOnClickListener {
                clickListener.onCurrencyClick(model.id)
            }
        }
    }
}