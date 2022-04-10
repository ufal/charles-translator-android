package cz.cuni.mff.ufal.translator.ui.translations.screens.widgets

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.launch
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.main.controller.IMainController
import cz.cuni.mff.ufal.translator.ui.VoiceContract
import cz.cuni.mff.ufal.translator.ui.translations.models.InputTextData
import cz.cuni.mff.ufal.translator.ui.translations.models.TextSource
import cz.cuni.mff.ufal.translator.ui.translations.viewmodel.ITranslationsViewModel

/**
 * @author Tomas Krabac
 */
@Composable
fun ActionsRow(
    viewModel: ITranslationsViewModel,
    mainController: IMainController,
    mainText: String,
) {
    val isSpeechRecognizerAvailable = viewModel.isSpeechRecognizerAvailable
    val isTextToSpeechAvailable by viewModel.isTextToSpeechAvailable.collectAsState()
    val inputLanguage by viewModel.inputLanguage.collectAsState()

    val voiceLauncher = rememberLauncherForActivityResult(VoiceContract(inputLanguage)) { text ->
        if (text != null) {
            viewModel.setInputText(InputTextData(text, TextSource.Voice))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Row(modifier = Modifier.align(Alignment.CenterStart)) {
            ActionItem(
                drawableRes = R.drawable.ic_history,
                contentDescriptionRes = R.string.history_cd
            ) {
                mainController.navigateHistory()
            }
        }

        Row(modifier = Modifier.align(Alignment.Center)) {
            if (isSpeechRecognizerAvailable) {
                ActionItem(
                    modifier = Modifier.padding(vertical = 4.dp),
                    size = 44.dp,
                    drawableRes = R.drawable.ic_mic,
                    contentDescriptionRes = R.string.speech_recognizer_cd
                ) {
                    voiceLauncher.launch()
                }
            }
        }

        Row(modifier = Modifier.align(Alignment.CenterEnd)) {
            if (mainText.isNotBlank()) {
                ActionItem(
                    drawableRes = R.drawable.ic_copy,
                    contentDescriptionRes = R.string.copy_to_clipboard_cd
                ) {
                    viewModel.copyToClipBoard(
                        text = mainText
                    )
                }

                if (isTextToSpeechAvailable) {
                    ActionItem(
                        drawableRes = R.drawable.ic_tts,
                        contentDescriptionRes = R.string.tts_cd
                    ) {
                        viewModel.textToSpeech()
                    }
                }
            }
        }
    }
}
