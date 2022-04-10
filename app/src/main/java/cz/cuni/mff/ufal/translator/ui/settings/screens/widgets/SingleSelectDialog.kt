package cz.cuni.mff.ufal.translator.ui.settings.screens.widgets

import androidx.compose.runtime.Composable
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.listItemsSingleChoice
import com.vanpra.composematerialdialogs.title

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
        dialogState = dialogState,
        buttons = {
            positiveButton("Ok")
        },
    ) {
        title(text = title)
        listItemsSingleChoice(list = items, initialSelection = initialSingleSelection, waitForPositiveButton = false) {
            onSelectedValue(it)
            dialogState.hide()
        }
    }
}

