package cz.cuni.mff.ufal.translator.ui.conversation.screens.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.cuni.mff.ufal.translator.ui.common.chatbubble.ArrowAlignment
import cz.cuni.mff.ufal.translator.ui.common.chatbubble.BubbleShadow
import cz.cuni.mff.ufal.translator.ui.common.chatbubble.BubbleState
import cz.cuni.mff.ufal.translator.ui.common.chatbubble.Padding
import cz.cuni.mff.ufal.translator.ui.common.chatbubble.drawBubble
import cz.cuni.mff.ufal.translator.ui.common.chatbubble.rememberBubbleState
import cz.cuni.mff.ufal.translator.ui.common.widgets.LanguageItem
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme
import cz.cuni.mff.ufal.translator.ui.theme.LindatThemePreview
import cz.cuni.mff.ufal.translator.ui.translations.models.Language
import cz.cuni.mff.ufal.translator.ui.translations.models.OutputTextData
import cz.cuni.mff.ufal.translator.ui.translations.screens.widgets.OutputText

/**
 * @author Tomas Krabac
 */
@Composable
fun LeftBubble(
    modifier: Modifier,
    data: OutputTextData,
    language: Language,
    showLanguage: Boolean,
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
        language = language,
        showLanguage = showLanguage,
    )
}

@Composable
fun RightBubble(
    modifier: Modifier,
    data: OutputTextData,
    language: Language,
    showLanguage: Boolean,
) {
    val bubbleState = rememberBubbleState(
        backgroundColor = LindatTheme.colors.primary,
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
        language = language,
        showLanguage = showLanguage,
    )
}

@Composable
private fun OutpuDataItem(
    modifier: Modifier,
    bubbleState: BubbleState,
    data: OutputTextData,
    language: Language,
    showLanguage: Boolean,
) {
    Column(
        modifier = modifier
            .drawBubble(bubbleState)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        val textColor = LindatTheme.colors.onPrimary
        if(showLanguage){
            LanguageItem(
                language = language,
                textColor = textColor,
            )
        }

        OutputText(
            modifier = Modifier,
            fontWeight = FontWeight.Bold,
            text = data.mainText,
            textColor = textColor,
        )

        OutputText(
            modifier = Modifier,
            fontWeight = FontWeight.Normal,
            text = data.secondaryText,
            textColor = textColor,
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
                ),
                language = Language.Czech,
                showLanguage = true,
            )

            Spacer(modifier = Modifier.height(16.dp))

            RightBubble(
                modifier = Modifier.align(Alignment.End),
                OutputTextData(
                    mainText = "main text",
                    secondaryText = "secondary text"
                ),
                language = Language.English,
                showLanguage = true,
            )
        }

    }
}
