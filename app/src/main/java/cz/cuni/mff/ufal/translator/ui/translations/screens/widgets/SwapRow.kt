package cz.cuni.mff.ufal.translator.ui.translations.screens.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.ui.common.widgets.ActionItem
import cz.cuni.mff.ufal.translator.ui.common.widgets.LanguageMenu
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme
import cz.cuni.mff.ufal.translator.ui.translations.models.Language

/**
 * @author Tomas Krabac
 */
@Composable
fun SwapRow(
    inputLanguage: Language,
    inputLanguages: List<Language>,
    outputLanguage: Language,
    outputLanguages: List<Language>,

    swapLanguages: () -> Unit,
    onInputLanguageSelect: (language: Language) -> Unit,
    onOutputLanguageSelect: (language: Language) -> Unit,
    onAboutAppClicked: () -> Unit,
    onSettingsClicked: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.weight(1F),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            LanguageMenu(
                modifier = Modifier.weight(1f),
                selectedLanguage = inputLanguage,
                languages = inputLanguages,
                onLanguageSelect = onInputLanguageSelect,
            )

            SwapItem(modifier = Modifier, onClick = swapLanguages)

            LanguageMenu(
                modifier = Modifier.weight(1f),
                selectedLanguage = outputLanguage,
                languages = outputLanguages,
                onLanguageSelect = onOutputLanguageSelect,
            )
        }

        Row {
            val state = rememberDropdownMenuState()
            ActionItem(drawableRes = R.drawable.ic_more, contentDescriptionRes = R.string.more_cd) {
                state.expand()
            }
            ShowMoreMenu(
                state = state,

                onAboutAppClicked = onAboutAppClicked,
                onSettingsClicked = onSettingsClicked,
            )
        }

    }
}

@Composable
private fun SwapItem(modifier: Modifier, onClick: () -> Unit) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_swap),
            tint = LindatTheme.colors.primary,
            contentDescription = stringResource(id = R.string.swap_cd),
        )
    }
}