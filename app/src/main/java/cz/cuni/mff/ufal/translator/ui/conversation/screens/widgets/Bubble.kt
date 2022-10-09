package cz.cuni.mff.ufal.translator.ui.conversation.screens.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.cuni.mff.ufal.translator.ui.common.chatbubble.*
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme
import cz.cuni.mff.ufal.translator.ui.theme.LindatThemePreview
import cz.cuni.mff.ufal.translator.ui.translations.models.OutputTextData
import cz.cuni.mff.ufal.translator.ui.translations.screens.widgets.OutputText

/**
 * @author Tomas Krabac
 */
@Composable
fun LeftBubble(
    modifier: Modifier,
    data: OutputTextData,
) {
    val bubbleState = rememberBubbleState(
        backgroundColor = LindatTheme.colors.primary,
        alignment = ArrowAlignment.LeftTop,
        cornerRadius = 8.dp,
        shadow = BubbleShadow(
            elevation = 1.dp
        ),
        padding = Padding(8.dp)
    )

    OutpuDataItem(
        modifier = modifier,
        bubbleState = bubbleState,
        data = data,
    )
}

@Composable
fun RightBubble(
    modifier: Modifier,
    data: OutputTextData,
) {
    val bubbleState = rememberBubbleState(
        backgroundColor = LindatTheme.colors.secondary,
        alignment = ArrowAlignment.RightTop,
        cornerRadius = 12.dp,
        shadow = BubbleShadow(
            elevation = 1.dp
        ),
        padding = Padding(8.dp)
    )

    OutpuDataItem(
        modifier = modifier,
        bubbleState = bubbleState,
        data = data,
    )
}

@Composable
private fun OutpuDataItem(
    modifier: Modifier,
    bubbleState: BubbleState,
    data: OutputTextData,
) {
    Column(
        modifier = modifier
            .drawBubble(bubbleState)
            .padding(horizontal = 8.dp, vertical = 4.dp)
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


@Preview(showBackground = true)
@Composable
private fun ActionsRow() {
    LindatThemePreview {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            LeftBubble(
                modifier = Modifier.align(Alignment.Start),
                OutputTextData(
                    mainText = "main text",
                    secondaryText = "secondary text"
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
            
            RightBubble(
                modifier = Modifier.align(Alignment.End),
                OutputTextData(
                    mainText = "main text",
                    secondaryText = "secondary text"
                )
            )
        }

    }
}
