package cz.cuni.mff.ufal.translator.main.controller

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cz.cuni.mff.ufal.translator.ui.about.screens.AboutScreen
import cz.cuni.mff.ufal.translator.ui.conversation.screens.ConversationScreen
import cz.cuni.mff.ufal.translator.ui.history.model.HistoryItem
import cz.cuni.mff.ufal.translator.ui.history.screens.MainHistoryScreen
import cz.cuni.mff.ufal.translator.ui.settings.screens.SettingsScreen
import cz.cuni.mff.ufal.translator.ui.translations.screens.TranslationsScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    mainController: MainController,
    startDestination: String = AppNavItem.Home.screenRoute,
) {
    NavHost(
        modifier = modifier,
        navController = mainController.navController,
        startDestination = startDestination
    ) {
        composable(AppNavItem.Home.screenRoute) { navBackResult ->
            val historyItem = navBackResult.savedStateHandle.get<HistoryItem>("history_item")
            navBackResult.savedStateHandle["history_item"] = null

            TranslationsScreen(
                mainController = mainController,
                historyItem = historyItem,
            )
        }
        composable(AppNavItem.About.screenRoute) {
            AboutScreen(
                mainController = mainController,
            )
        }
        composable(AppNavItem.Settings.screenRoute) {
            SettingsScreen(
                mainController = mainController,
            )
        }
        composable(AppNavItem.History.screenRoute) {
            MainHistoryScreen(
                mainController = mainController,
            )
        }
        composable(AppNavItem.Conversation.screenRoute) {
            ConversationScreen(
                mainController = mainController,
            )
        }
    }
}