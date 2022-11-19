package suvorov.coin.utils

import suvorov.coin.R

sealed class Screen(val title: String) {
    object List: Screen("Список валют")
    object Favorites: Screen("Избранное")
    object Detail: Screen("Валюта")
}

data class BottomNavigationItem(
    val route: String,
    val icon: Int
)

val bottomNavigationItems = listOf(
    BottomNavigationItem(
        Screen.List.title,
        R.drawable.ic_baseline_view_list_24
    ),
    BottomNavigationItem(
        Screen.Favorites.title,
        R.drawable.ic_baseline_star_24
    )
)