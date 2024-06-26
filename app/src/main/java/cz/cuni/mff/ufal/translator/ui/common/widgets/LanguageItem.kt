package cz.cuni.mff.ufal.translator.ui.common.widgets

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme
import cz.cuni.mff.ufal.translator.ui.translations.models.Language

/**
 * @author Tomas Krabac
 */
@Composable
fun LanguageItem(
    modifier: Modifier = Modifier,
    language: Language,
    textColor: Color = LindatTheme.colors.onSurface,
) {
    Text(
        modifier = modifier,
        text = stringResource(id = language.labelRes),
        color = textColor
    )
}