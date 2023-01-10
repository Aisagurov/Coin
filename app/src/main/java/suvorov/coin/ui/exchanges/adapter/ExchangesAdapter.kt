package suvorov.coin.ui.exchanges.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import suvorov.coin.databinding.ExchangesItemBinding
import suvorov.coin.domain.model.Exchange
import suvorov.coin.ui.exchanges.adapter.clicklistener.ExchangesClickListener

class ExchangesAdapter(
    private val clickListener: ExchangesClickListener
): ListAdapter<Exchange, ExchangesViewHolder>(AsyncDifferConfig.Builder(DiffCallback).build()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangesViewHolder {
        return ExchangesViewHolder(
            ExchangesItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ExchangesViewHolder, position: Int) {
        holder.bind(currentList[position], clickListener)
    }

    object DiffCallback: DiffUtil.ItemCallback<Exchange>() {
        override fun areItemsTheSame(oldItem: Exchange, newItem: Exchange): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: Exchange, newItem: Exchange): Boolean {
            return oldItem.id == newItem.id
        }
    }
}