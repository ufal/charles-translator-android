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
import com.ramcosta.composedestinations.navigation.navigateTo
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.ui.destinations.AboutScreenDestination
import cz.cuni.mff.ufal.translator.ui.destinations.MainHistoryScreenDestination
import cz.cuni.mff.ufal.translator.ui.destinations.SettingsScreenDestination


/**
 * @author Tomas Krabac
 */
class MainController(
    val navController: NavHostController,
    private val context: Context,
    override val isDarkMode: Boolean,
) : IMainController {
    override fun navigateHistory() {
        navController.navigateTo(MainHistoryScreenDestination)
    }

    override fun navigateAboutScreen() {
        navController.navigateTo(AboutScreenDestination)
    }

    override fun navigateSettingsScreen() {
        navController.navigateTo(SettingsScreenDestination)
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
}

@Composable
fun rememberMainController(isDarkMode: Boolean): MainController {
    val navController = rememberNavController()
    val context = LocalContext.current
    return remember {
        MainController(
            navController = navController,
            context = context,
            isDarkMode = isDarkMode,
        )
    }
}