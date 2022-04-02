package cz.cuni.mff.lindat.main.controller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cz.cuni.mff.lindat.history.data.HistoryItem

/**
 * @author Tomas Krabac
 */
class Controller(val navController: NavHostController) : IController {
    override fun navigateHistory() {
        navController.navigate("history")
    }

    override fun navigateMainScreen(item: HistoryItem) {
        navController.navigate("main/${item.text}")
    }

    override fun onBackPressed() {
        navController.popBackStack()
    }
}

@Composable
fun rememberController(): Controller {
    val navController = rememberNavController()
    return remember {
        Controller(navController)
    }
}