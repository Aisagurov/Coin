package suvorov.coin.ui.portfolio.adapter

import androidx.recyclerview.widget.RecyclerView
import suvorov.coin.databinding.PortfolioItemBinding
import suvorov.coin.domain.model.Currency
import suvorov.coin.ui.currencies.adapter.clicklistener.CurrenciesClickListener
import suvorov.coin.util.*

class PortfolioViewHolder(
    private val binding: PortfolioItemBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(model: Currency, clickListener: CurrenciesClickListener) {
        binding.apply {
            rankTextView.text = model.rank.toString()
            imageView.load(model.image)
            symbolTextView.text = model.symbol.uppercase()
            priceTextView.text = model.price.priceDollarString()
            PriceHelper.showPriceChange(priceChangeTextView, model.priceChange)
            capTextView.text = model.cap.dollarString()
            root.setOnClickListener {
                clickListener.onCurrencyClick(model.id)
            }
        }
    }
}