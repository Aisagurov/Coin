package suvorov.coin.ui.categories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import suvorov.coin.databinding.CategoriesItemBinding
import suvorov.coin.domain.model.Category
import suvorov.coin.ui.categories.adapter.clicklistener.CategoriesClickListener

class CategoriesAdapter(
    private val clickListener: CategoriesClickListener
): ListAdapter<Category, CategoriesViewHolder>(AsyncDifferConfig.Builder(DiffCallback).build()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            CategoriesItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(currentList[position], clickListener)
    }

    object DiffCallback: DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }
    }
}