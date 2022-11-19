package suvorov.coin.presentation.ui.layout

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.patrykandpatryk.vico.compose.axis.axisLabelComponent
import com.patrykandpatryk.vico.compose.axis.axisLineComponent
import com.patrykandpatryk.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatryk.vico.compose.axis.vertical.startAxis
import com.patrykandpatryk.vico.compose.chart.Chart
import com.patrykandpatryk.vico.compose.chart.line.lineChart
import com.patrykandpatryk.vico.compose.chart.line.lineSpec
import com.patrykandpatryk.vico.core.axis.horizontal.HorizontalAxis
import com.patrykandpatryk.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatryk.vico.core.entry.FloatEntry
import suvorov.coin.R
import suvorov.coin.data.remote.HistoricalPrice
import suvorov.coin.presentation.ui.viewmodel.HistoryViewModel
import suvorov.coin.utils.ChartHelper.getDate
import suvorov.coin.utils.Constants.TEN_DAYS
import suvorov.coin.utils.Constants.THIRTY_DAYS
import suvorov.coin.utils.Constants.TWENTY_DAYS
import suvorov.coin.utils.PriceHelper
import suvorov.coin.utils.dollarString
import suvorov.coin.utils.emptyIfNull

@Composable
fun DetailView(id: String, symbol: String, viewModel: HistoryViewModel, popBackStack: () -> Unit) {
    val coin = viewModel.coinFromSymbol(symbol).observeAsState().value
    var list by remember { mutableStateOf(HistoricalPrice(emptyList()).prices) }
    LaunchedEffect(true) { list = viewModel.getHistoricalPrice(id).prices }
    var textLast = ""
    var textTen = ""
    var textTwenty = ""
    var textFirst = ""

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val (
            topAppBar,
            price,
            changePrice,
            arrowIcon,
            chart,
            dateFirst,
            dateTen,
            dateTwenty,
            dateLast,
            progressIndicator
        ) = createRefs()

        TopAppBar(
            title = {
                AsyncImage(
                    model = coin?.image.emptyIfNull(),
                    modifier = Modifier.size(34.dp),
                    contentDescription = null
                )
                Text(
                    text = coin?.name.emptyIfNull(),
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp)
                )
            },
            navigationIcon = {
                IconButton(onClick = { popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            },
            backgroundColor = colorResource(R.color.dark_grey),
            modifier = Modifier.constrainAs(topAppBar) {
                top.linkTo(parent.top)
            }
        )

        Text(
            text = coin?.price.dollarString(),
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(price) {
                start.linkTo(parent.start, 8.dp)
                top.linkTo(topAppBar.bottom, 8.dp)
            }
        )

        Text(
            text = PriceHelper.showValuePrice(coin?.changePrice),
            fontSize = 20.sp,
            color = if(PriceHelper.showValuePrice(coin?.changePrice).contains("-")) {
                Color.Red
            } else {
                Color.Green
            },
            modifier = Modifier.constrainAs(changePrice) {
                start.linkTo(price.end, 16.dp)
                bottom.linkTo(price.bottom)
            },
        )

        Icon(
            imageVector = if(PriceHelper.showValuePrice(coin?.changePrice).contains("-")) {
                Icons.Filled.KeyboardArrowDown
            } else {
                Icons.Filled.KeyboardArrowUp
            },
            contentDescription = null,
            tint = if(PriceHelper.showValuePrice(coin?.changePrice).contains("-")) {
                Color.Red
            } else {
                Color.Green
            },
            modifier = Modifier.constrainAs(arrowIcon) {
                start.linkTo(changePrice.end, 8.dp)
                bottom.linkTo(changePrice.bottom)
            }
        )

        if (list.isNotEmpty()) {
            val producer = ChartEntryModelProducer(list.mapIndexed { x, y ->
                FloatEntry(x = x.toFloat(), y = y[1].toFloat())
            })

            val lineChart = lineChart(
                lines = listOf(lineSpec(
                    lineColor = colorResource(R.color.blue)
                ))
            )

            Chart(
                chart = lineChart,
                chartModelProducer = producer,
                startAxis = startAxis(
                    label = axisLabelComponent(
                        color = Color.White
                    ),
                    axis = axisLineComponent(
                        color = Color.White,
                        thickness = 1.dp,
                        brush = null
                    )
                ),
                bottomAxis = bottomAxis(
                    label = axisLabelComponent(
                        color = colorResource(R.color.dark_grey)
                    ),
                    tickPosition = HorizontalAxis.TickPosition.Center(
                        spacing = 120
                    ),
                    axis = axisLineComponent(
                        color = Color.White,
                        thickness = 1.dp,
                        brush = null
                    )
                ),
                isHorizontalScrollEnabled = false,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 4.dp, end = 4.dp, top = 116.dp)
                    .constrainAs(chart) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }

        list.mapIndexed { _, doubles ->
            textLast = getDate(doubles[0])
            textTwenty = getDate(doubles[0] - TWENTY_DAYS)
            textTen = getDate(doubles[0] - TEN_DAYS)
            textFirst = getDate(doubles[0] - THIRTY_DAYS)
        }

        Text(
            text = textLast,
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier.constrainAs(dateLast) {
                end.linkTo(chart.end, 4.dp)
                bottom.linkTo(chart.bottom)
            }
        )

        Text(
            text = textTwenty,
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier.constrainAs(dateTwenty) {
                start.linkTo(dateTen.start, 60.dp)
                end.linkTo(dateLast.start)
                bottom.linkTo(chart.bottom)
            }
        )

        Text(
            text = textTen,
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier.constrainAs(dateTen) {
                start.linkTo(chart.start, 66.dp)
                end.linkTo(dateTwenty.start)
                bottom.linkTo(chart.bottom)
            }
        )

        Text(
            text = textFirst,
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier.constrainAs(dateFirst) {
                start.linkTo(chart.start, 46.dp)
                bottom.linkTo(chart.bottom)
            }
        )

        viewModel.isLoading.observeAsState().value?.let {
            if(it) {
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
