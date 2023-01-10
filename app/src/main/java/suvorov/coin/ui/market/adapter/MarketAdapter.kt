package suvorov.coin.ui.market.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import suvorov.coin.ui.categories.CategoriesFragment
import suvorov.coin.ui.currencies.CurrenciesFragment
import suvorov.coin.ui.derivatives.DerivativesFragment
import suvorov.coin.ui.exchanges.ExchangesFragment

class MarketAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    data class Page(val fragment: () -> Fragment)

    private val pages = listOf(
        Page { CurrenciesFragment() },
        Page { CategoriesFragment() },
        Page { ExchangesFragment() },
        Page { DerivativesFragment() }
    )

    override fun getItemCount(): Int {
        return pages.size
    }

    override fun createFragment(position: Int): Fragment {
        return pages[position].fragment()
    }
}