package cz.cuni.mff.ufal.translator.ui.translations.screens

import android.content.Context
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.interactors.ContextUtils

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
                        ContextUtils.openGooglePlay(context, GOOGLE_TTS_PACKAGE)
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