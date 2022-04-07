package cz.cuni.mff.ufal.translator.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.ui.translations.models.Language

/**
 * @author Tomas Krabac
 */
@Composable
fun FlagItem(language: Language) {
    val iconRes = when (language) {
        Language.Czech -> R.drawable.ic_czech_flag
        Language.Ukrainian -> R.drawable.ic_ukraine_flag
    }

    val iconContentDescriptionRes = when (language) {
        Language.Czech -> R.string.czech_flag_cd
        Language.Ukrainian -> R.string.ukraine_flag_cd
    }

    Image(
        modifier = Modifier.height(16.dp),
        painter = painterResource(id = iconRes),
        contentDescription = stringResource(id = iconContentDescriptionRes),
    )
}