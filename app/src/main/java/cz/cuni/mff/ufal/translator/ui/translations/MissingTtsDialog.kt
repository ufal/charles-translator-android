package cz.cuni.mff.ufal.translator.ui.translations

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import cz.cuni.mff.ufal.translator.R

/**
 * @author Tomas Krabac
 */

private const val GOOGLE_TTS_PACKAGE = "com.google.android.tts"

@Composable
fun MissingTtsDialog(isDialogVisible: MutableState<Boolean>, context: Context) {

    if (isDialogVisible.value) {
        AlertDialog(
            onDismissRequest = {
                // clicks outside dialog are disabled
            },
            title = {
                Text(stringResource(id = R.string.dialog_tts_title))
            },
            text = {
                Text(stringResource(id = R.string.dialog_tts_message))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openGooglePlay(context)
                        isDialogVisible.value = false

                    }) {
                    Text(stringResource(id = R.string.dialog_tts_download))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        isDialogVisible.value = false
                    }) {
                    Text(stringResource(id = R.string.dialog_tts_cancel))
                }
            },
        )
    }
}

private fun openGooglePlay(context: Context) {
    try {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$GOOGLE_TTS_PACKAGE")))
    } catch (e: ActivityNotFoundException) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$GOOGLE_TTS_PACKAGE")
            )
        )
    }
}