package cz.cuni.mff.ufal.translator.ui.translations.screens.widgets

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @author Tomas Krabac
 */

@Composable
fun ActionItem(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    @DrawableRes drawableRes: Int,
    @StringRes contentDescriptionRes: Int,
    onClick: () -> Unit,
) {

    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier.size(size),
            painter = painterResource(id = drawableRes),
            tint = MaterialTheme.colors.primary,
            contentDescription = stringResource(id = contentDescriptionRes),
        )
    }
}