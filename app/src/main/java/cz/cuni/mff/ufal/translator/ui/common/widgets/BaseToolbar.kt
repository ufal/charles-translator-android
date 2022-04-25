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
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme

/**
 * @author Tomas Krabac
 */
@Composable
fun BaseToolbar(
    @StringRes titleRes: Int,
    onBackPressed: () -> Unit,
) {
    TopAppBar(
        backgroundColor = LindatTheme.colors.toolbarBackground,
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    tint = LindatTheme.colors.onPrimary,
                    contentDescription = ""
                )
            }
        },
        title = {
            Text(
                text = stringResource(titleRes),
                color = LindatTheme.colors.onPrimary,
            )
        },
        modifier = Modifier.statusBarsPadding(),
        elevation = 4.dp,
    )
}
