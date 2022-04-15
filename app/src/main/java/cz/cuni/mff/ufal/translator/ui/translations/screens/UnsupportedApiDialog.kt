package cz.cuni.mff.ufal.translator.ui.translations.screens

import android.content.Context
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.interactors.ContextUtils
import cz.cuni.mff.ufal.translator.interactors.api.data.NotImplementedData

/**
 * @author Tomas Krabac
 */
private const val LINDAT_TRANSLATOR_PACKAGE = "cz.cuni.mff.ufal.translator"

@Composable
fun UnsupportedApiDialog(context: Context, data: NotImplementedData) {

    val title = data.title.ifBlank { stringResource(id = R.string.dialog_unsupported_api_title) }
    val message = data.message.ifBlank { stringResource(id = R.string.dialog_unsupported_api_message) }

    AlertDialog(
        onDismissRequest = {
            // clicks outside dialog are disabled
        },
        title = {
            Text(title)
        },
        text = {
            Text(message)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    ContextUtils.openGooglePlay(context, LINDAT_TRANSLATOR_PACKAGE)


                }) {
                Text(stringResource(id = R.string.dialog_unsupported_api_download))
            }
        },
    )
}