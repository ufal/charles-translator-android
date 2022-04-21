package cz.cuni.mff.ufal.translator.ui.history.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import cz.cuni.mff.ufal.translator.main.controller.IMainController
import cz.cuni.mff.ufal.translator.ui.history.model.HistoryItem
import cz.cuni.mff.ufal.translator.ui.history.screens.menu.HistoryNavigationGraph
import cz.cuni.mff.ufal.translator.ui.history.screens.widgets.HistoryBottomNavigation
import cz.cuni.mff.ufal.translator.ui.history.viewmodel.HistoryViewModel
import cz.cuni.mff.ufal.translator.ui.history.viewmodel.IHistoryViewModel
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme

/**
 * @author Tomas Krabac
 */
@Destination()
@Composable
fun MainHistoryScreen(
    mainController: IMainController,
    viewModel: IHistoryViewModel = hiltViewModel<HistoryViewModel>(),
    resultNavigator: ResultBackNavigator<HistoryItem>,
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
                resultNavigator = resultNavigator,
            )
        }
    }
}