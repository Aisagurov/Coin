package suvorov.coin.ui.market

import android.os.Bundle
import android.view.View
import suvorov.coin.R
import suvorov.coin.databinding.FragmentMarketBinding
import suvorov.coin.ui.base.BaseFragment
import suvorov.coin.ui.market.adapter.MarketAdapter
import suvorov.coin.util.setupWithViewPager

class MarketFragment: BaseFragment<FragmentMarketBinding>(FragmentMarketBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    private fun initView() {
        binding.viewPager.adapter = MarketAdapter(this)
        binding.tabLayout.setupWithViewPager(binding.viewPager, listOf(
            getString(R.string.cryptocurrency),
            getString(R.string.categories),
            getString(R.string.exchanges),
            getString(R.string.derivatives))
        )
    }
}