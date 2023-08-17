package cz.cuni.mff.ufal.translator.ui.common.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.ui.theme.LindatThemePreview
import cz.cuni.mff.ufal.translator.ui.translations.models.Language

@Composable
fun LanguageMenu(
    modifier: Modifier = Modifier,
    selectedLanguage: Language,
    languages: List<Language>,
    onLanguageSelect: (language: Language) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var columnWidth by remember {
        mutableStateOf(0)
    }

    Column(
        modifier = modifier
            .clickable { expanded = !expanded }
            .onSizeChanged {
                columnWidth = it.width
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {

            FlagLabelItem(
                language = selectedLanguage,
            )

            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_drop_down),
                contentDescription = null,
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            languages.forEach { language ->
                LanguageMenuItem(
                    modifier = Modifier.widthIn(columnWidth.pxToDp()),
                    isSelected = language == selectedLanguage,
                    language = language,
                    onClick = {
                        expanded = false
                        onLanguageSelect(language)
                    },
                )
            }
        }
    }
}

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

@Composable
fun LanguageMenuItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    language: Language,
    onClick: () -> Unit,
) {
    DropdownMenuItem(
        modifier = modifier,
        content = {
            Text(
                text = stringResource(id = language.labelRes),
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        },
        onClick = onClick,
    )
}

@Preview(showBackground = true)
@Composable
private fun LanguageMenuPreview() {
    LindatThemePreview {
        LanguageMenu(
            selectedLanguage = Language.Czech,
            languages = listOf(Language.Czech),
            onLanguageSelect = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LanguageMenuItemPreview() {
    LindatThemePreview {
        LanguageMenuItem(
            isSelected = false,
            language = Language.Czech,
            onClick = {},
        )
    }
}