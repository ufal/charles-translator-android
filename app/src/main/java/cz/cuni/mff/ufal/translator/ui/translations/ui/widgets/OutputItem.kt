package cz.cuni.mff.ufal.translator.ui.translations.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cz.cuni.mff.ufal.translator.ui.translations.models.OutputTextData

/**
 * @author Tomas Krabac
 */
@Composable
fun OutputItem(modifier: Modifier, data: OutputTextData) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .border(2.dp, MaterialTheme.colors.primary, RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        OutputText(
            modifier = Modifier,
            fontWeight = FontWeight.Bold,
            text = data.mainText,
        )

        OutputText(
            modifier = Modifier,
            fontWeight = FontWeight.Normal,
            text = data.secondaryText,
        )
    }
}