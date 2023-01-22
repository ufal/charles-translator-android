package cz.cuni.mff.ufal.translator.ui.common.widgets

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/**
 * @author Tomas Krabac
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MicrophoneItem(
    snackbarHostState: SnackbarHostState,
    isSpeechRecognizerAvailable: Boolean,
    isListening: Boolean,
    rmsdB: Float,

    startRecognizeAudio: () -> Unit,
    stopRecognizeAudio: () -> Unit,
) {
    val audioPermissionState = if (LocalInspectionMode.current) {
        object : PermissionState {
            override val permission = ""
            override val status = PermissionStatus.Granted
            override fun launchPermissionRequest() {}
        }
    } else {
        rememberPermissionState(
            permission = Manifest.permission.RECORD_AUDIO
        )
    }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    if (isSpeechRecognizerAvailable) {
        val icon = if (isListening) {
            R.drawable.ic_mic_stop
        } else {
            R.drawable.ic_mic
        }

        Box(
            modifier = Modifier.padding(4.dp),
            contentAlignment = Alignment.Center
        ) {

            if (isListening) {
                Box(
                    modifier = Modifier
                        .size(40.dp + rmsdB.dp)
                        .clip(CircleShape)
                        .background(LindatTheme.colors.primary.copy(alpha = 0.4f)),
                    contentAlignment = Alignment.Center
                ) {
                }
            }

            ActionItem(
                size = 36.dp,
                drawableRes = icon,
                contentDescriptionRes = R.string.speech_recognizer_cd
            ) {
                val status = audioPermissionState.status

                when {
                    status == PermissionStatus.Granted -> {
                        if (isListening) {
                            stopRecognizeAudio()
                        } else {
                            startRecognizeAudio()
                        }
                    }
                    status is PermissionStatus.Denied && !status.shouldShowRationale -> {
                        audioPermissionState.launchPermissionRequest()
                    }
                    status is PermissionStatus.Denied && status.shouldShowRationale -> {
                        showNoPermissionSnackBar(
                            snackbarHostState = snackbarHostState,
                            scope = coroutineScope,
                            context = context,
                        )
                    }
                }
            }
        }
    }
}

private fun showNoPermissionSnackBar(
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    context: Context,
) {
    scope.launch {
        val result = snackbarHostState.showSnackbar(
            message = context.resources.getString(R.string.snackbar_no_audio_record_permisson),
            actionLabel = context.resources.getString(R.string.snackbar_app_settings),
        )
        if (result == SnackbarResult.ActionPerformed) {
            showAppSettings(context)
        }
    }
}

private fun showAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts("package", context.packageName, null)
    intent.data = uri
    context.startActivity(intent)
}
