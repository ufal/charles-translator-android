package cz.cuni.mff.ufal.translator.ui.translations.screens

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme

/**
 * @author Tomas Krabac
 */
@Composable
fun FirstStartDialog(onAgreeAction: (agree: Boolean) -> Unit) {
    val openDialog = remember { mutableStateOf(true) }


    if (openDialog.value) {

        AlertDialog(
            backgroundColor = LindatTheme.colors.dialogBackgound,
            onDismissRequest = {
                // clicks outside dialog are disabled
            },
            title = {
                Text(stringResource(id = R.string.dialog_data_processing_title))
            },
            text = {
                Text(stringResource(id = R.string.dialog_data_processing_message))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        onAgreeAction(true)
                    }) {
                    Text(stringResource(id = R.string.dialog__data_processing_agree))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        onAgreeAction(false)
                    }) {
                    Text(stringResource(id = R.string.dialog__data_processing_disagree))
                }
            },
        )
    }
}