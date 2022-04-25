package cz.cuni.mff.ufal.translator.ui.translations.screens.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme

/**
 * @author Tomas Krabac
 */
@Composable
fun ErrorItem(modifier: Modifier, retryAction: () -> Unit) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.api_error),
            color = LindatTheme.colors.onBackground,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(14.dp))
        OutlinedButton(
            shape = RoundedCornerShape(50),
            onClick = retryAction,
            border = BorderStroke(1.dp, color = LindatTheme.colors.onBackground),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = LindatTheme.colors.background,
                contentColor = LindatTheme.colors.onBackground,
            )
        ) {
            Text(
                text = stringResource(id = R.string.try_again).uppercase(),
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
            )
        }
    }
}