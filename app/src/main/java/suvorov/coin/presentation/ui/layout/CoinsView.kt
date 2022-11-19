package suvorov.coin.presentation.ui.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import suvorov.coin.R
import suvorov.coin.presentation.ui.viewmodel.ListViewModel

@Composable
fun CoinsView(viewModel: ListViewModel, onCoinSelected: (id: String, symbol: String) -> Unit) {
    val list = viewModel.coinsListFromDatabase.observeAsState().value.orEmpty()
    viewModel.getCoinsList()

    ConstraintLayout(
        modifier = Modifier
            .padding(bottom = 60.dp)
            .fillMaxSize(),
    ) {
        val(topAppBar, listLazyColumn, progressIndicator) = createRefs()

        TopAppBar(
            backgroundColor = colorResource(R.color.dark_grey),
            modifier = Modifier.constrainAs(topAppBar) {
                top.linkTo(parent.top)
            }
        ) {
            Box(Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    modifier = Modifier.align(Alignment.Center),
                    contentDescription = null
                )
            }
        }

        LazyColumn(
            modifier = Modifier.constrainAs(listLazyColumn) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(topAppBar.bottom, 4.dp)
            }
        ) {
            itemsIndexed(items = list) { _, coin ->
                ListItem(coin = coin, onCoinSelected = onCoinSelected, viewModel = viewModel)
            }
        }

        viewModel.isLoading.observeAsState().value?.let {
            if(viewModel.isListEmpty() && it) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.grey),
                    modifier = Modifier
                        .size(34.dp)
                        .constrainAs(progressIndicator) {
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