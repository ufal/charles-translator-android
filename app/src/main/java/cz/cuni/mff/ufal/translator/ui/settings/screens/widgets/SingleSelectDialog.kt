package cz.cuni.mff.ufal.translator.ui.settings.screens.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme

/**
 * @author Tomas Krabac
 */

@Composable
fun SingleSelectDialog(
    title: String,
    optionsList: List<String>,
    selectedItem: String,
    submitButtonText: String,
    dialogState: DialogState,
    onSelectItem: (Int) -> Unit,
) {
    if (dialogState.isVisible) {
        var selectedOptionIndex by remember {
            mutableStateOf(optionsList.indexOf(selectedItem))
        }

        Dialog(onDismissRequest = { dialogState.hide() }) {
            Surface(
                modifier = Modifier.defaultMinSize(minWidth = 300.dp),
                shape = RoundedCornerShape(10.dp)
            ) {

                Column(
                    modifier = Modifier
                        .background(LindatTheme.colors.dialogBackgound)
                        .padding(16.dp)
                ) {

                    Text(text = title)

                    Spacer(modifier = Modifier.defaultMinSize(minWidth = 10.dp))

                    optionsList.forEach {
                        RadioButtonItem(it, optionsList[selectedOptionIndex]) { selectedValue ->
                            selectedOptionIndex = optionsList.indexOf(selectedValue)
                            onSelectItem.invoke(selectedOptionIndex)
                            dialogState.hide()
                        }
                    }

                    Spacer(modifier = Modifier.defaultMinSize(minWidth = 10.dp))

                    Button(
                        modifier = Modifier.align(Alignment.End),
                        onClick = {
                            onSelectItem.invoke(selectedOptionIndex)
                            dialogState.hide()
                        },
                    ) {
                        Text(text = submitButtonText)
                    }
                }

            }
        }
    }
}

@Composable
fun RadioButtonItem(text: String, selectedValue: String, onClickListener: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = (text == selectedValue),
                onClick = {
                    onClickListener(text)
                }
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = (text == selectedValue),
            colors = RadioButtonDefaults.colors(
                selectedColor = LindatTheme.colors.selected
            ),
            onClick = {
                onClickListener(text)
            }
        )
        Text(
            text = text,
            style = MaterialTheme.typography.body1.merge(),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

class DialogState(initialValue: Boolean = false) {
    var isVisible by mutableStateOf(initialValue)

    fun show() {
        isVisible = true
    }

    fun hide() {
        isVisible = false
    }

}

@Composable
fun rememberDialogState(initialValue: Boolean = false): DialogState {
    return remember {
        DialogState(initialValue)
    }
}

