package suvorov.coin.ui.currencies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import suvorov.coin.databinding.CurrenciesItemBinding
import suvorov.coin.domain.model.Currency
import suvorov.coin.ui.currencies.adapter.diffutil.DiffCallback
import suvorov.coin.ui.currencies.adapter.clicklistener.CurrenciesClickListener

class CurrenciesAdapter(
    private val clickListener: CurrenciesClickListener
): ListAdapter<Currency, CurrenciesViewHolder>(AsyncDifferConfig.Builder(DiffCallback).build()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrenciesViewHolder {
        return CurrenciesViewHolder(
            CurrenciesItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CurrenciesViewHolder, position: Int) {
        holder.bind(currentList[position], clickListener)
    }
}