package suvorov.coin.ui.derivatives.adapter

import androidx.recyclerview.widget.RecyclerView
import suvorov.coin.databinding.DerivativesItemBinding
import suvorov.coin.domain.model.Derivative
import suvorov.coin.ui.derivatives.adapter.clicklistener.DerivativesClickListener
import suvorov.coin.util.btcString
import suvorov.coin.util.load

class DerivativesViewHolder(
    private val binding: DerivativesItemBinding
): RecyclerView.ViewHolder(binding.root) {
    val rank = binding.rankTextView
    fun bind(model: Derivative, clickListener: DerivativesClickListener) {
        binding.apply {
            imageView.load(model.image)
            nameTextView.text = model.name
            interestTextView.text = model.interest.btcString()
            volumeTextView.text = model.volume.toDouble().btcString()
            root.setOnClickListener {
                clickListener.onDerivativeClick(model.name)
            }
        }
    }
}