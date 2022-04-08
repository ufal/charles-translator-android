package cz.cuni.mff.ufal.translator.ui.history.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import cz.cuni.mff.ufal.translator.main.controller.IMainController
import cz.cuni.mff.ufal.translator.ui.history.screens.menu.HistoryNavigationGraph
import cz.cuni.mff.ufal.translator.ui.history.screens.widgets.HistoryBottomNavigation
import cz.cuni.mff.ufal.translator.ui.history.viewmodel.IHistoryViewModel
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme

/**
 * @author Tomas Krabac
 */
@Composable
fun MainHistoryScreen(
    mainController: IMainController,
    viewModel: IHistoryViewModel,
) {
    LindatTheme {
        val historyController = rememberNavController()
        Scaffold(
            bottomBar = { HistoryBottomNavigation(navController = historyController) }
        ) { innerPadding ->
            HistoryNavigationGraph(
                modifier = Modifier.padding(innerPadding),
                mainController = mainController,
                historyController = historyController,
                viewModel = viewModel,
            )
        }
    }
}