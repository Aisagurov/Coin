package suvorov.coin.ui.portfolio.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import suvorov.coin.databinding.PortfolioItemBinding
import suvorov.coin.domain.model.Currency
import suvorov.coin.ui.currencies.adapter.clicklistener.CurrenciesClickListener
import suvorov.coin.ui.currencies.adapter.diffutil.DiffCallback

class PortfolioAdapter(
    private val clickListener: CurrenciesClickListener
): ListAdapter<Currency, PortfolioViewHolder>(AsyncDifferConfig.Builder(DiffCallback).build()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioViewHolder {
        return PortfolioViewHolder(
            PortfolioItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PortfolioViewHolder, position: Int) {
        holder.bind(currentList[position], clickListener)
    }
}