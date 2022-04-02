package cz.cuni.mff.lindat.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cz.cuni.mff.lindat.history.ui.HistoryScreen
import cz.cuni.mff.lindat.history.viewmodel.HistoryViewModel
import cz.cuni.mff.lindat.history.viewmodel.IHistoryViewModel
import cz.cuni.mff.lindat.main.controller.rememberController
import cz.cuni.mff.lindat.main.ui.MainScreen
import cz.cuni.mff.lindat.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val historyViewModel: IHistoryViewModel by viewModels<HistoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val controller = rememberController()

            NavHost(controller.navController, startDestination = "main") {
                composable("main") {
                    MainScreen(
                        viewModel = mainViewModel,
                        controller = controller
                    )
                }
                composable("history") {
                    HistoryScreen(
                        viewModel = historyViewModel,
                        controller = controller
                    )
                }
            }
        }
    }
}

