package cz.cuni.mff.ufal.translator.ui.history.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import cz.cuni.mff.ufal.translator.main.controller.IMainController
import cz.cuni.mff.ufal.translator.ui.history.screens.menu.HistoryNavHost
import cz.cuni.mff.ufal.translator.ui.history.screens.widgets.HistoryBottomNavigation
import cz.cuni.mff.ufal.translator.ui.history.viewmodel.HistoryViewModel
import cz.cuni.mff.ufal.translator.ui.history.viewmodel.IHistoryViewModel
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme

/**
 * @author Tomas Krabac
 */
@Composable
fun MainHistoryScreen(
    mainController: IMainController,
    viewModel: IHistoryViewModel = hiltViewModel<HistoryViewModel>(),
) {
    val isDarkMode by mainController.darkModeSetting.collectAsState()

    LindatTheme(isDarkMode) {
        val historyController = rememberNavController()
        Scaffold(
            bottomBar = { HistoryBottomNavigation(navController = historyController) }
        ) { innerPadding ->
            HistoryNavHost(
                modifier = Modifier.padding(innerPadding),
                mainController = mainController,
                historyController = historyController,
                viewModel = viewModel,
            )
        }
    }
}