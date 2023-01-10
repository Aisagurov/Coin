package suvorov.coin.ui.categories.adapter

import androidx.recyclerview.widget.RecyclerView
import suvorov.coin.databinding.CategoriesItemBinding
import suvorov.coin.domain.model.Category
import suvorov.coin.ui.categories.adapter.clicklistener.CategoriesClickListener
import suvorov.coin.util.*

class CategoriesViewHolder(
    private val binding: CategoriesItemBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(model: Category, clickListener: CategoriesClickListener) {
        binding.apply {
            firstImageView.load(model.firstTop)
            secondImageView.load(model.secondTop)
            thirdImageView.load(model.thirdTop)
            nameTextView.text = model.name
            marketCupTextView.text = model.marketCap.dollarString()
            PriceHelper.showPriceChange(changeTextView, model.change)
            root.setOnClickListener {
                clickListener.onCategoryClick(model.id)
            }
        }
    }
}