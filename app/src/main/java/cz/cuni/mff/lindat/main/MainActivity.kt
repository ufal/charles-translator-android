package cz.cuni.mff.lindat.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import cz.cuni.mff.lindat.history.data.HistoryItem
import cz.cuni.mff.lindat.history.ui.HistoryScreen
import cz.cuni.mff.lindat.history.viewmodel.HistoryViewModel
import cz.cuni.mff.lindat.main.controller.rememberController
import cz.cuni.mff.lindat.main.ui.MainScreen
import cz.cuni.mff.lindat.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val controller = rememberController()

            NavHost(controller.navController, startDestination = "main/{item}") {
                composable(
                    "main/{item}",
                    arguments = listOf(navArgument("item") { type = NavType.StringType })
                ) { backStackEntry ->
                    val viewModel by viewModels<MainViewModel>()

                    val json = backStackEntry.arguments?.getString("item")
                    if (!json.isNullOrEmpty()) {
                        val historyItem = Json.decodeFromString<HistoryItem>(json)
                        viewModel.setFromHistoryItem(historyItem)
                    }

                    MainScreen(
                        viewModel = viewModel,
                        controller = controller
                    )
                }
                composable("history") {
                    val viewModel by viewModels<HistoryViewModel>()
                    HistoryScreen(
                        viewModel = viewModel,
                        controller = controller
                    )
                }
            }
        }
    }
}

