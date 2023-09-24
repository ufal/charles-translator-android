package cz.cuni.mff.ufal.translator.ui.common.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme
import cz.cuni.mff.ufal.translator.ui.translations.models.Language

/**
 * @author Tomas Krabac
 */
@Composable
fun FlagLabelItem(
    modifier: Modifier = Modifier,
    language: Language,
    textColor: Color = LindatTheme.colors.onSurface,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        FlagItem(language = language)

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = stringResource(id = language.labelRes),
            color = textColor
        )
    }

}

@Composable
private fun FlagItem(language: Language) {
    val iconRes = when (language) {
        Language.Czech -> R.drawable.ic_flag_czech
        Language.Ukrainian -> R.drawable.ic_flag_ukraine
        Language.English -> R.drawable.ic_flag_english
        Language.French -> R.drawable.ic_flag_french
        Language.Polish -> R.drawable.ic_flag_polish
        Language.Russian -> R.drawable.ic_flag_russian
    }

    val iconContentDescriptionRes = when (language) {
        Language.Czech -> R.string.czech_flag_cd
        Language.Ukrainian -> R.string.ukraine_flag_cd
        Language.English -> R.string.english_flag_cd
        Language.French -> R.string.french_flag_cd
        Language.Polish -> R.string.polish_flag_cd
        Language.Russian -> R.string.russian_flag_cd
    }

    Image(
        modifier = Modifier.height(16.dp),
        painter = painterResource(id = iconRes),
        contentDescription = stringResource(id = iconContentDescriptionRes),
    )
}