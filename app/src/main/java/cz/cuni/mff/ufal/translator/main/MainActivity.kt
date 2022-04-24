package cz.cuni.mff.ufal.translator.main

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import cz.cuni.mff.ufal.translator.extensions.isTablet
import cz.cuni.mff.ufal.translator.interactors.crashlytics.FirebaseHelper.setBaseFirebaseInfo
import cz.cuni.mff.ufal.translator.main.controller.rememberMainController
import cz.cuni.mff.ufal.translator.ui.NavGraphs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isTablet) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
        }

        setBaseFirebaseInfo(this)

        setContent {
            val controller = rememberMainController()

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

