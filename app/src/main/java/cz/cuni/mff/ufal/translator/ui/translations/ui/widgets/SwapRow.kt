package cz.cuni.mff.ufal.translator.ui.translations.ui.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.ui.common.FlagItem
import cz.cuni.mff.ufal.translator.ui.translations.models.Language

/**
 * @author Tomas Krabac
 */
@Composable
fun SwapRow(
    inputLanguage: Language,
    outputLanguage: Language,

    swapLanguages: () -> Unit,
    showMore: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.weight(1F),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Label(modifier = Modifier.weight(1f), language = inputLanguage)

            SwapItem(modifier = Modifier, onClick = swapLanguages)

            Label(modifier = Modifier.weight(1f), language = outputLanguage)
        }

        ActionItem(drawableRes = R.drawable.ic_more, contentDescriptionRes = R.string.more_cd) {
            showMore()
        }
    }
}

@Composable
private fun Label(modifier: Modifier = Modifier, language: Language) {
    val labelRes = when (language) {
        Language.Czech -> R.string.cz_label
        Language.Ukrainian -> R.string.uk_label
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        FlagItem(language = language)

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = stringResource(id = labelRes),
            color = MaterialTheme.colors.onSurface
        )
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
            tint = MaterialTheme.colors.primary,
            contentDescription = stringResource(id = R.string.swap_cd),
        )
    }
}