package cz.cuni.mff.ufal.translator.ui.translations.screens.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.interactors.tts.TextToSpeechError
import cz.cuni.mff.ufal.translator.main.controller.IMainController
import cz.cuni.mff.ufal.translator.main.controller.PreviewIMainController
import cz.cuni.mff.ufal.translator.ui.common.widgets.ActionItem
import cz.cuni.mff.ufal.translator.ui.common.widgets.MicrophoneItem
import cz.cuni.mff.ufal.translator.ui.theme.LindatThemePreview
import cz.cuni.mff.ufal.translator.ui.translations.screens.TtsErrorDialog
import cz.cuni.mff.ufal.translator.ui.translations.viewmodel.ITranslationsViewModel
import cz.cuni.mff.ufal.translator.ui.translations.viewmodel.PreviewTranslationsViewModel

/**
 * @author Tomas Krabac
 */
@Composable
fun ActionsRow(
    viewModel: ITranslationsViewModel,
    mainController: IMainController,
    mainText: String,
) {
    val isTtsErrorDialogVisible = remember { mutableStateOf(false) }
    val isSpeechRecognizerAvailable = viewModel.isSpeechRecognizerAvailable
    val isListening by viewModel.isListening.collectAsState()
    val rmsdB by viewModel.rmsdB.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.textToSpeechErrors.collect {
            if (it == TextToSpeechError.SpeakError) {
                isTtsErrorDialogVisible.value = true
            }
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

            ActionItem(
                size = 30.dp,
                drawableRes = R.drawable.ic_conversation,
                contentDescriptionRes = R.string.conversation_cd
            ) {
                mainController.navigateConversationScreen()
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .size(54.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            MicrophoneItem(
                snackbarHostState = snackbarHostState,
                isSpeechRecognizerAvailable = isSpeechRecognizerAvailable,
                isListening = isListening,
                rmsdB = rmsdB,

                startRecognizeAudio = viewModel::startRecognizeAudio,
                stopRecognizeAudio = viewModel::stopRecognizeAudio,
            )
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

                ActionItem(
                    drawableRes = R.drawable.ic_tts,
                    contentDescriptionRes = R.string.tts_cd
                ) {
                    viewModel.textToSpeech()
                }
            }
        }

        TtsErrorDialog(isTtsErrorDialogVisible, LocalContext.current)

        SnackbarHost(hostState = snackbarHostState)
    }
}

@Preview(showBackground = true)
@Composable
private fun ActionsRowPreview() {
    LindatThemePreview {
        ActionsRow(
            viewModel = PreviewTranslationsViewModel(),
            mainController = PreviewIMainController(),
            mainText = "text"
        )
    }
}
