package suvorov.coin.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import suvorov.coin.databinding.SearchItemBinding
import suvorov.coin.domain.model.Currency
import suvorov.coin.ui.currencies.adapter.diffutil.DiffCallback
import suvorov.coin.ui.currencies.adapter.clicklistener.CurrenciesClickListener

class SearchAdapter(
    private val clickListener: CurrenciesClickListener
): ListAdapter<Currency, SearchViewHolder>(AsyncDifferConfig.Builder(DiffCallback).build()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            SearchItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(currentList[position], clickListener)
    }
}