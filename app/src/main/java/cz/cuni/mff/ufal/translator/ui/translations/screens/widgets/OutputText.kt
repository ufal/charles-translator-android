package cz.cuni.mff.ufal.translator.ui.translations.screens.widgets

import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    textColor: Color,
) {
    SelectionContainer(modifier) {
        Text(
            text = text,
            style = TextStyle(
                fontWeight = fontWeight,
                fontSize = 22.sp,
            ),
            color = textColor,
        )
    }
}