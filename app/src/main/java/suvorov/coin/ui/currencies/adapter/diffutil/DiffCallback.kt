package suvorov.coin.ui.currencies.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import suvorov.coin.domain.model.Currency

object DiffCallback: DiffUtil.ItemCallback<Currency>() {
    override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem.id == newItem.id
    }
}