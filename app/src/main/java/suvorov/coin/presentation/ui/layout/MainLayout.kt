package suvorov.coin.presentation.ui.layout

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import suvorov.coin.R
import suvorov.coin.presentation.ui.viewmodel.HistoryViewModel
import suvorov.coin.presentation.ui.viewmodel.ListViewModel
import suvorov.coin.utils.Constants.COIN_ID
import suvorov.coin.utils.bottomNavigationItems
import suvorov.coin.utils.Constants.COIN_SYMBOL
import suvorov.coin.utils.Screen
import suvorov.coin.utils.emptyIfNull

@Composable
fun MainLayout(listViewModel: ListViewModel, historyViewModel: HistoryViewModel) {
    val navController = rememberNavController()
    Scaffold(
        backgroundColor = colorResource(R.color.dark_grey),
        bottomBar = { MainBottomNavigation(navController) }) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.List.title, modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.List.title) {
                CoinsView(
                    viewModel = listViewModel,
                    onCoinSelected = { id, symbol ->
                        navController.navigate(Screen.Detail.title + "/${id}" + "/${symbol}")
                    })
            }
            composable(Screen.Favorites.title) {
                FavoritesView(
                    viewModel = listViewModel,
                    onCoinSelected = { id, symbol ->
                        navController.navigate(Screen.Detail.title + "/${id}" + "/${symbol}")
                    })
            }
            composable(
                Screen.Detail.title + "/{$COIN_ID}" + "/{$COIN_SYMBOL}",
                arguments = listOf(
                    navArgument(COIN_ID) { type = NavType.StringType },
                    navArgument(COIN_SYMBOL) { type = NavType.StringType })
                ) {
                val id = it.arguments?.getString(COIN_ID)
                val symbol = it.arguments?.getString(COIN_SYMBOL)
                DetailView(
                    id = id.emptyIfNull(),
                    symbol = symbol.emptyIfNull(),
                    viewModel = historyViewModel,
                    popBackStack = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
fun MainBottomNavigation(navController: NavHostController) {
    BottomNavigation(backgroundColor = colorResource(R.color.grey)) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        bottomNavigationItems.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(item.icon), null) },
                label = { Text(item.route) },
                selected = currentRoute == item.route,
                selectedContentColor = Color.Green,
                unselectedContentColor = Color.Gray,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}