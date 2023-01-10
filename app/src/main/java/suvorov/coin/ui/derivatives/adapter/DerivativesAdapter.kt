package suvorov.coin.ui.derivatives.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import suvorov.coin.databinding.DerivativesItemBinding
import suvorov.coin.domain.model.Derivative
import suvorov.coin.ui.derivatives.adapter.clicklistener.DerivativesClickListener

class DerivativesAdapter(
    private val clickListener: DerivativesClickListener
): ListAdapter<Derivative, DerivativesViewHolder>(AsyncDifferConfig.Builder(DiffCallback).build()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DerivativesViewHolder {
        return DerivativesViewHolder(
            DerivativesItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: DerivativesViewHolder, position: Int) {
        holder.bind(currentList[position], clickListener)
        val rank = (position + 1).toString()
        holder.rank.text = rank
    }

    object DiffCallback: DiffUtil.ItemCallback<Derivative>() {
        override fun areItemsTheSame(oldItem: Derivative, newItem: Derivative): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: Derivative, newItem: Derivative): Boolean {
            return oldItem.id == newItem.id
        }
    }
}