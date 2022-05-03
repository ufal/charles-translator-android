package cz.cuni.mff.ufal.translator.ui.settings.screens.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.listItemsSingleChoice
import com.vanpra.composematerialdialogs.title
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme

/**
 * @author Tomas Krabac
 */
/**
 * @brief Single Selection List Dialog Demos
 */
@Composable
fun SingleSelectionDialog(
    dialogState: MaterialDialogState,
    title: String,
    selectedItem: String,
    items: List<String>,

    onSelectedValue: (Int) -> Unit,
) {
    val initialSingleSelection = items.indexOf(selectedItem)

    MaterialDialog(
        backgroundColor = LindatTheme.colors.dialogBackgound,
        dialogState = dialogState,
        buttons = {
            positiveButton(stringResource(id = android.R.string.ok))
        },
    ) {
        title(text = title)
        listItemsSingleChoice(list = items, initialSelection = initialSingleSelection, waitForPositiveButton = false) {
            onSelectedValue(it)
            dialogState.hide()
        }
    }
}

