package cz.cuni.mff.ufal.translator.main

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import cz.cuni.mff.ufal.translator.extensions.isTablet
import cz.cuni.mff.ufal.translator.interactors.crashlytics.FirebaseHelper.setBaseFirebaseInfo
import cz.cuni.mff.ufal.translator.interactors.preferences.IUserDataStore
import cz.cuni.mff.ufal.translator.main.controller.rememberMainController
import cz.cuni.mff.ufal.translator.ui.NavGraphs
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var dataStore: IUserDataStore

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isTablet) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
        }

        setBaseFirebaseInfo(this)

        setContent {
            val isDarkMode = runBlocking {
                dataStore.isExperimentalDarkMode.first()
            }

            val controller = rememberMainController(isDarkMode)

            LindatTheme(isDarkMode) {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(
                    color = LindatTheme.colors.toolbarBackground
                )
            }

            DestinationsNavHost(
                navGraph = NavGraphs.root,
                navController = controller.navController,
                dependenciesContainerBuilder = {
                    dependency(controller)
                }
            )
        }
    }
}

