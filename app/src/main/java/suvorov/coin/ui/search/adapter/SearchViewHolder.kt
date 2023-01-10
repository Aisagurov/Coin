package suvorov.coin.ui.search.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import suvorov.coin.R
import suvorov.coin.databinding.SearchItemBinding
import suvorov.coin.domain.model.Currency
import suvorov.coin.ui.currencies.adapter.clicklistener.CurrenciesClickListener
import suvorov.coin.util.load

class SearchViewHolder(
    private val binding: SearchItemBinding
): RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind(model: Currency, clickListener: CurrenciesClickListener) {
        binding.apply {
            rankTextView.text = "${root.context.getString(R.string.octothorp)}${model.rank}"
            imageView.load(model.image)
            symbolTextView.text = model.symbol.uppercase()
            nameTextView.text = model.name
            root.setOnClickListener {
                clickListener.onCurrencyClick(model.id)
            }
        }
    }
}