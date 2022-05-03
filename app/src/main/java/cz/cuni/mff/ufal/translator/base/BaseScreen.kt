package cz.cuni.mff.ufal.translator.base

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.insets.ProvideWindowInsets
import cz.cuni.mff.ufal.translator.interactors.crashlytics.FirebaseHelper.setFirebaseScreen
import cz.cuni.mff.ufal.translator.interactors.crashlytics.Screen
import cz.cuni.mff.ufal.translator.interactors.preferences.data.DarkModeSetting
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Tomas Krabac
 */
@Composable
fun BaseScreen(screen: Screen, darkModeSetting: StateFlow<DarkModeSetting>, viewModel: IBaseViewModel? = null, content: @Composable () -> Unit) {
    setFirebaseScreen(screen)
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel?.onStart()
            } else if (event == Lifecycle.Event.ON_STOP) {
                viewModel?.onStop()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val state by darkModeSetting.collectAsState()

    LindatTheme(state) {
        ProvideWindowInsets {
            Surface(
                modifier = Modifier.fillMaxSize(),
                content = content,
            )
        }
    }
}