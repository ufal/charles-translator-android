package cz.cuni.mff.ufal.translator.main.controller


import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.interactors.preferences.data.DarkModeSetting
import cz.cuni.mff.ufal.translator.ui.history.model.HistoryItem
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @author Tomas Krabac
 */
class MainController(
    val navController: NavHostController,
    private val context: Context,
) : IMainController {

    override val darkModeSetting = MutableStateFlow(DarkModeSetting.System)

    override fun navigateHistory() {
        navController.navigate(AppNavItem.History.screenRoute)
    }

    override fun navigateAboutScreen() {
        navController.navigate(AppNavItem.About.screenRoute)
    }

    override fun navigateSettingsScreen() {
        navController.navigate(AppNavItem.Settings.screenRoute)
    }

    override fun navigateConversationScreen() {
        navController.navigate(AppNavItem.Conversation.screenRoute)
    }

    override fun openWebUrl(url: String) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent: CustomTabsIntent = builder.build()
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }

    override fun sendMail(mail: String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$mail")
        }

        try {
            context.startActivity(emailIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, R.string.mail_app_not_found, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        navController.popBackStack()
    }

    override fun setDarkModeSettings(darkModeSetting: DarkModeSetting) {
        this.darkModeSetting.value = darkModeSetting
    }

    override fun setHistoryItem(item: HistoryItem) {
        navController.popBackStack()
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.set("history_item", item)
    }
}

@Composable
fun rememberMainController(): MainController {
    val navController = rememberNavController()
    val context = LocalContext.current
    return remember {
        MainController(
            navController = navController,
            context = context,
        )
    }
}