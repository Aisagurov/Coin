package suvorov.coin.presentation.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.SubcomposeAsyncImage
import suvorov.coin.data.local.database.CoinEntity
import suvorov.coin.utils.ToastHelper.showToast
import suvorov.coin.R
import suvorov.coin.presentation.ui.viewmodel.ListViewModel
import suvorov.coin.utils.PriceHelper.showValuePrice
import suvorov.coin.utils.dollarString
import suvorov.coin.utils.emptyIfNull

@Composable
fun ListItem(
    coin: CoinEntity,
    viewModel: ListViewModel,
    onCoinSelected: (id: String, symbol: String) -> Unit
) {
    var isFavorite by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Card(
        backgroundColor = colorResource(R.color.dark_green),
        shape = RoundedCornerShape(0.dp),
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(top = 1.dp, bottom = 1.dp)
            .clickable { onCoinSelected(coin.id.emptyIfNull(), coin.symbol) },
    ) {
        ConstraintLayout {
            val(image, symbol, price, changePrice, arrowIcon, favoritesButton) = createRefs()

            SubcomposeAsyncImage(
                model = coin.image.emptyIfNull(),
                loading = { CircularProgressIndicator() },
                contentDescription = null,
                modifier = Modifier
                    .size(34.dp)
                    .constrainAs(image) {
                        start.linkTo(parent.start, 16.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )

            Text(
                text = coin.symbol.uppercase(),
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.constrainAs(symbol) {
                    start.linkTo(image.end, 16.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            )

            Text(
                text = coin.price.dollarString(),
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.constrainAs(price) {
                    start.linkTo(image.end, 90.dp)
                    bottom.linkTo(symbol.bottom)
                }
            )

            Text(
                text = showValuePrice(coin.changePrice),
                fontSize = 16.sp,
                color = if(showValuePrice(coin.changePrice).contains("-")) {
                    Color.Red
                } else {
                    Color.Green
                },
                modifier = Modifier.constrainAs(changePrice) {
                    end.linkTo(arrowIcon.start, 8.dp)
                    bottom.linkTo(symbol.bottom)
                },
            )

            Icon(
                imageVector = if(showValuePrice(coin.changePrice).contains("-")) {
                    Icons.Filled.KeyboardArrowDown
                } else {
                    Icons.Filled.KeyboardArrowUp
                },
                contentDescription = null,
                tint = if(showValuePrice(coin.changePrice).contains("-")) {
                    Color.Red
                } else {
                    Color.Green
                },
                modifier = Modifier.constrainAs(arrowIcon) {
                    start.linkTo(image.end, 250.dp)
                    bottom.linkTo(changePrice.bottom)
                }
            )

            IconToggleButton(
                checked = isFavorite,
                modifier = Modifier.constrainAs(favoritesButton) {
                    end.linkTo(parent.end, 8.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
                onCheckedChange = {
                    isFavorite = !isFavorite

                    viewModel.updateFavoritesStatus(coin.symbol)

                    showToast(context,
                        if (coin.isFavorite)
                            "${coin.name} ${context.getString(R.string.removed_from_favorites)}"
                        else
                            "${coin.name} ${context.getString(R.string.added_to_favorites)}"
                    )
                }) {
                Icon(
                    painter = if (coin.isFavorite) {
                        painterResource(id = R.drawable.ic_baseline_star_24)
                    } else {
                        painterResource(id = R.drawable.ic_baseline_star_border_24)
                    },
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        }
    }
}