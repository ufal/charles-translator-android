package cz.cuni.mff.lindat.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import cz.cuni.mff.lindat.history.ui.HistoryScreen
import cz.cuni.mff.lindat.history.viewmodel.HistoryViewModel
import cz.cuni.mff.lindat.main.controller.rememberController
import cz.cuni.mff.lindat.main.ui.MainScreen
import cz.cuni.mff.lindat.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val controller = rememberController()

            // TODO: 02.04.2022 tomaskrabac: dodelat ostatni parametry
            NavHost(controller.navController, startDestination = "main/{text}") {
                composable("main/{text}",
                    arguments = listOf(navArgument("text") { type = NavType.StringType })
                ) { backStackEntry ->
                    val text = backStackEntry.arguments?.getString("text") ?: ""

                    val viewModel by viewModels<MainViewModel>()
                    viewModel.setInputText(text)
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

