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
import cz.cuni.mff.ufal.translator.interactors.ContextUtils.isPackageInstalled
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme

/**
 * @author Tomas Krabac
 */

private const val GOOGLE_TTS_PACKAGE = "com.google.android.tts"

@Composable
fun TtsErrorDialog(isDialogVisible: MutableState<Boolean>, context: Context){
    if(isPackageInstalled(context, GOOGLE_TTS_PACKAGE)){
        GeneralErrorDialog(isDialogVisible)
    }
    else{
        MissingTtsDialog(isDialogVisible, context)
    }
}

@Composable
private fun GeneralErrorDialog(isDialogVisible: MutableState<Boolean>) {
    if (isDialogVisible.value) {
        AlertDialog(
            backgroundColor = LindatTheme.colors.dialogBackgound,
            onDismissRequest = {
                // clicks outside dialog are disabled
            },
            title = {
                Text(stringResource(id = R.string.dialog_tts_title))
            },
            text = {
                Text(stringResource(id = R.string.dialog_tts_message_unknown_error))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        isDialogVisible.value = false
                    }) {
                    Text(stringResource(id = android.R.string.ok))
                }
            },
        )
    }
}

@Composable
private fun MissingTtsDialog(isDialogVisible: MutableState<Boolean>, context: Context) {

    if (isDialogVisible.value) {
        AlertDialog(
            backgroundColor = LindatTheme.colors.dialogBackgound,
            onDismissRequest = {
                // clicks outside dialog are disabled
            },
            title = {
                Text(stringResource(id = R.string.dialog_tts_title))
            },
            text = {
                Text(stringResource(id = R.string.dialog_tts_message_missing_google_tts))
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