package suvorov.coin.presentation.ui.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import suvorov.coin.R
import suvorov.coin.data.local.database.CoinEntity
import suvorov.coin.presentation.ui.viewmodel.ListViewModel

@Composable
fun FavoritesView(viewModel: ListViewModel, onCoinSelected: (id: String, symbol: String) -> Unit) {
    val list = viewModel.favoritesListFromDatabase.observeAsState().value.orEmpty()

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val(topAppBar, favoritesLazyColumn, favoritesText) = createRefs()

        TopAppBar(
            backgroundColor = colorResource(R.color.dark_grey),
            modifier = Modifier.constrainAs(topAppBar) {
                top.linkTo(parent.top)
            },
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = stringResource(R.string.title_favorites),
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
        }

        LazyColumn(
            modifier = Modifier.constrainAs(favoritesLazyColumn) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(topAppBar.bottom, 4.dp)
            }
        ) {
            items(items = list, { coin: CoinEntity -> coin.symbol }) { coin ->
                ListItem(coin = coin, viewModel, onCoinSelected = onCoinSelected)
            }
        }

        viewModel.favoritesListFromDatabase.observeAsState().value?.let {
            if(it.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.favorites_empty),
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .constrainAs(favoritesText) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                )
            }
        }
    }
}