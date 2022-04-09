package cz.cuni.mff.ufal.translator.ui.translations.screens.widgets

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cz.cuni.mff.ufal.translator.R

/**
 * @author Tomas Krabac
 */
@Composable
fun ShowMoreMenu(
    state: DropdownMenuState,

    onAboutAppClicked: () -> Unit,
    onSettingsClicked: () -> Unit,
) {
    DropdownMenu(
        expanded = state.expanded,
        onDismissRequest = { state.collapse() }
    ) {
        DropdownMenuItem(
            onClick = {
                state.collapse()
                onAboutAppClicked()
            },
            content = {
                Text(text = stringResource(id = R.string.about_title))
            }
        )

        DropdownMenuItem(
            onClick = {
                state.collapse()
                onSettingsClicked()
            },
            content = {
                Text(text = stringResource(id = R.string.settings_title))
            }
        )
    }
}

class DropdownMenuState(
    expanded: Boolean,
) {
    var expanded by mutableStateOf(expanded)
        private set

    fun expand() {
        expanded = true
    }

    fun collapse() {
        expanded = false
    }
}

@Composable
fun rememberDropdownMenuState(
    initiallyExpanded: Boolean = false,
) = remember {
    DropdownMenuState(initiallyExpanded)
}
