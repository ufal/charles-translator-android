package cz.cuni.mff.ufal.translator.ui.common.widgets

import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import cz.cuni.mff.ufal.translator.R

/**
 * @author Tomas Krabac
 */
@Composable
fun BaseToolbar(
    @StringRes titleRes: Int,
    onBackPressed: () -> Unit,
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    tint = MaterialTheme.colors.onPrimary,
                    contentDescription = ""
                )
            }
        },
        title = {
            Text(
                text = stringResource(titleRes),
                color = MaterialTheme.colors.onPrimary,
            )
        },
        modifier = Modifier.statusBarsPadding(),
        elevation = 4.dp,
    )
}
