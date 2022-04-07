package cz.cuni.mff.ufal.translator.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import cz.cuni.mff.ufal.translator.history.data.HistoryItem
import cz.cuni.mff.ufal.translator.history.ui.HistoryScreen
import cz.cuni.mff.ufal.translator.history.viewmodel.HistoryViewModel
import cz.cuni.mff.ufal.translator.main.controller.rememberController
import cz.cuni.mff.ufal.translator.ui.translations.ui.TranslationsScreen
import cz.cuni.mff.ufal.translator.ui.translations.viewmodel.TranslationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val controller = rememberController()

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

