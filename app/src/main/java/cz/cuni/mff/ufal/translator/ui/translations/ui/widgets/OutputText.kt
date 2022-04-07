package cz.cuni.mff.ufal.translator.ui.translations.ui.widgets

import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * @author Tomas Krabac
 */
@Composable
fun OutputText(
    modifier: Modifier,
    fontWeight: FontWeight,
    text: String,
) {
    SelectionContainer(modifier) {
        Text(
            text = text,
            style = TextStyle(
                fontWeight = fontWeight,
                fontSize = 22.sp,
            ),
        )
    }
}