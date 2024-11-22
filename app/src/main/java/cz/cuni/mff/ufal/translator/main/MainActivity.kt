package cz.cuni.mff.ufal.translator.main

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import cz.cuni.mff.ufal.translator.extensions.isTablet
import cz.cuni.mff.ufal.translator.interactors.analytics.IAnalytics
import cz.cuni.mff.ufal.translator.interactors.preferences.IUserDataStore
import cz.cuni.mff.ufal.translator.main.controller.AppNavHost
import cz.cuni.mff.ufal.translator.main.controller.rememberMainController
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var dataStore: IUserDataStore

    @Inject
    lateinit var analytics: IAnalytics

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isTablet) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
        }

        analytics.setBaseInfo(this)

        setContent {
            var isDarkMode by remember {
                mutableStateOf(runBlocking { dataStore.darkModeSetting.first() })
            }

            val mainController = rememberMainController()

            LaunchedEffect(Unit) {
                dataStore.darkModeSetting.collect {
                    isDarkMode = it
                    mainController.setDarkModeSettings(it)
                }
            }

            LindatTheme(isDarkMode) {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(
                    color = LindatTheme.colors.statusBar
                )

                AppNavHost(mainController = mainController)
            }
        }
    }
}

