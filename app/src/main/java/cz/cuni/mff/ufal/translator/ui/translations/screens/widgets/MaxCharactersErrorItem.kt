package cz.cuni.mff.ufal.translator.ui.translations.screens.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme
import cz.cuni.mff.ufal.translator.ui.translations.screens.TranslationsScreen
import cz.cuni.mff.ufal.translator.ui.translations.viewmodel.TranslationsViewModel

/**
 * @author Tomas Krabac
 */
@Composable
fun MaxCharactersErrorItem(modifier: Modifier) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.max_limit_error, TranslationsViewModel.MAX_CHARACTERS),
            color = LindatTheme.colors.onBackground,
            fontSize = 18.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}