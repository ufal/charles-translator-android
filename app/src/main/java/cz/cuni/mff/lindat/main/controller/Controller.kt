package cz.cuni.mff.lindat.main.controller


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cz.cuni.mff.lindat.history.data.HistoryItem
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


/**
 * @author Tomas Krabac
 */
class Controller(val navController: NavHostController) : IController {
    override fun navigateHistory() {
        navController.navigate("history")
    }

    override fun navigateMainScreen(item: HistoryItem) {
        val json = Json.encodeToString(item)
        navController.navigate("main/${json}")
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