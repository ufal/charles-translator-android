package cz.cuni.mff.ufal.translator.ui.history.screens.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cz.cuni.mff.ufal.translator.ui.history.model.HistoryItem
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme

/**
 * @author Tomas Krabac
 */
@Composable
fun TextContent(modifier: Modifier = Modifier, item: HistoryItem) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = item.inputText,
            color = LindatTheme.colors.onBackground,
            maxLines = 10,
            overflow = TextOverflow.Ellipsis,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Divider()

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = item.outputText,
            fontWeight = FontWeight.Bold,
            color = LindatTheme.colors.onBackground,
            maxLines = 10,
            overflow = TextOverflow.Ellipsis,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = item.secondaryText,

            color = LindatTheme.colors.onBackground,
            maxLines = 10,
            overflow = TextOverflow.Ellipsis,
        )
    }

}
