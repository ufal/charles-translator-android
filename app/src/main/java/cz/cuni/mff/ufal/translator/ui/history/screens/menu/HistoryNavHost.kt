package cz.cuni.mff.ufal.translator.ui.history.screens.menu

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cz.cuni.mff.ufal.translator.main.controller.IMainController
import cz.cuni.mff.ufal.translator.ui.history.screens.HistoryAllScreen
import cz.cuni.mff.ufal.translator.ui.history.screens.HistoryFavouritesScreen
import cz.cuni.mff.ufal.translator.ui.history.viewmodel.IHistoryViewModel

/**
 * @author Tomas Krabac
 */
@Composable
fun HistoryNavHost(
    modifier: Modifier,
    mainController: IMainController,
    historyController: NavHostController,
    viewModel: IHistoryViewModel,
) {
    BackHandler {
        mainController.onBackPressed()
    }

    NavHost(
        modifier = modifier,
        navController = historyController,
        startDestination = HistoryNavItem.All.screenRoute
    ) {
        composable(HistoryNavItem.All.screenRoute) {
            HistoryAllScreen(
                viewModel = viewModel,
                mainController = mainController,
            )
        }

        composable(HistoryNavItem.Favourites.screenRoute) {
            HistoryFavouritesScreen(
                viewModel = viewModel,
                mainController = mainController,
            )
        }

    }
}