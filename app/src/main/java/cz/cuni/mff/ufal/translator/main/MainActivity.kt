package cz.cuni.mff.ufal.translator.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import cz.cuni.mff.ufal.translator.main.controller.rememberMainController
import cz.cuni.mff.ufal.translator.ui.about.screens.AboutScreen
import cz.cuni.mff.ufal.translator.ui.history.model.HistoryItem
import cz.cuni.mff.ufal.translator.ui.history.screens.MainHistoryScreen
import cz.cuni.mff.ufal.translator.ui.history.viewmodel.HistoryViewModel
import cz.cuni.mff.ufal.translator.ui.translations.screens.TranslationsScreen
import cz.cuni.mff.ufal.translator.ui.translations.viewmodel.TranslationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // TODO: 08.04.2022 tomaskrabac: refactor as History navigation
            val controller = rememberMainController()

            NavHost(controller.navController, startDestination = "translations/{item}") {
                composable(
                    "translations/{item}",
                    arguments = listOf(navArgument("item") { type = NavType.StringType })
                ) { backStackEntry ->
                    val viewModel by viewModels<TranslationsViewModel>()

                    val json = backStackEntry.arguments?.getString("item")
                    if (!json.isNullOrEmpty()) {
                        val historyItem = Json.decodeFromString<HistoryItem>(json)
                        viewModel.setFromHistoryItem(historyItem)
                    }

                    TranslationsScreen(
                        viewModel = viewModel,
                        mainController = controller
                    )
                }
                composable("history") {
                    val viewModel by viewModels<HistoryViewModel>()
                    MainHistoryScreen(
                        viewModel = viewModel,
                        mainController = controller
                    )
                }
                composable("about") {
                    AboutScreen(
                        mainController = controller
                    )
                }
            }
        }
    }
}

