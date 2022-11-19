package suvorov.coin.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.koin.androidx.viewmodel.ext.android.viewModel
import suvorov.coin.presentation.ui.layout.MainLayout
import suvorov.coin.presentation.ui.viewmodel.HistoryViewModel
import suvorov.coin.presentation.ui.viewmodel.ListViewModel

class MainActivity : ComponentActivity() {
    private val listViewModel: ListViewModel by viewModel()
    private val historyViewModel: HistoryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainLayout(listViewModel, historyViewModel)
        }
    }
}