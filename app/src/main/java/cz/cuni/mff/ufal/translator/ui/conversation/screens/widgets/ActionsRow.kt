package cz.cuni.mff.ufal.translator.ui.conversation.screens.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.cuni.mff.ufal.translator.ui.common.widgets.FlagLabelItem
import cz.cuni.mff.ufal.translator.ui.common.widgets.LanguageMenu
import cz.cuni.mff.ufal.translator.ui.common.widgets.MicrophoneItem
import cz.cuni.mff.ufal.translator.ui.theme.LindatThemePreview
import cz.cuni.mff.ufal.translator.ui.translations.models.Language

/**
 * @author Tomas Krabac
 */
@Composable
fun ActionsRow(
    isSpeechRecognizerAvailable: Boolean,
    isListening: Boolean,
    rmsdB: Float,
    activeLanguage: Language,
    leftLanguage: Language,
    rightLanguage: Language,
    rightLanguages: List<Language>,

    startRecognizeAudio: (language: Language) -> Unit,
    stopRecognizeAudio: () -> Unit,
    onLanguageSelect: (language: Language) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Row {
        Spacer(modifier = Modifier.width(16.dp))

        MicrophoneRow(
            snackbarHostState = snackbarHostState,
            isSpeechRecognizerAvailable = isSpeechRecognizerAvailable,
            isListening = isListening && activeLanguage == leftLanguage,
            rmsdB = rmsdB,
            language = leftLanguage,

            startRecognizeAudio = { startRecognizeAudio(leftLanguage) },
            stopRecognizeAudio = stopRecognizeAudio,
        )

        Spacer(modifier = Modifier.weight(1F))

        MicrophoneRow(
            snackbarHostState = snackbarHostState,
            isSpeechRecognizerAvailable = isSpeechRecognizerAvailable,
            isListening = isListening && activeLanguage == rightLanguage,
            rmsdB = rmsdB,
            selectedLanguage = rightLanguage,
            languages = rightLanguages,

            startRecognizeAudio = { startRecognizeAudio(rightLanguage) },
            stopRecognizeAudio = stopRecognizeAudio,
            onLanguageSelect = onLanguageSelect,
        )

        Spacer(modifier = Modifier.width(16.dp))
    }

    SnackbarHost(hostState = snackbarHostState)
}

@Composable
private fun MicrophoneRow(
    snackbarHostState: SnackbarHostState,
    isSpeechRecognizerAvailable: Boolean,
    isListening: Boolean,
    rmsdB: Float,
    language: Language,

    startRecognizeAudio: () -> Unit,
    stopRecognizeAudio: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MicrophoneItem(
            snackbarHostState = snackbarHostState,
            isSpeechRecognizerAvailable = isSpeechRecognizerAvailable,
            isListening = isListening,
            rmsdB = rmsdB,

            startRecognizeAudio = startRecognizeAudio,
            stopRecognizeAudio = stopRecognizeAudio,
        )

        FlagLabelItem(
            language = language,
        )
    }
}

@Composable
private fun MicrophoneRow(
    snackbarHostState: SnackbarHostState,
    isSpeechRecognizerAvailable: Boolean,
    isListening: Boolean,
    rmsdB: Float,
    selectedLanguage: Language,
    languages: List<Language>,

    startRecognizeAudio: () -> Unit,
    stopRecognizeAudio: () -> Unit,
    onLanguageSelect: (language: Language) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MicrophoneItem(
            snackbarHostState = snackbarHostState,
            isSpeechRecognizerAvailable = isSpeechRecognizerAvailable,
            isListening = isListening,
            rmsdB = rmsdB,

            startRecognizeAudio = startRecognizeAudio,
            stopRecognizeAudio = stopRecognizeAudio,
        )

        LanguageMenu(
            selectedLanguage = selectedLanguage,
            languages = languages,
            onLanguageSelect = onLanguageSelect,
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun ActionsRow() {
    LindatThemePreview {
        ActionsRow(
            isSpeechRecognizerAvailable = true,
            isListening = true,
            rmsdB = 3F,
            activeLanguage = Language.Ukrainian,
            leftLanguage = Language.Czech,
            rightLanguage = Language.Ukrainian,
            rightLanguages = listOf(Language.Ukrainian),

            startRecognizeAudio = {},
            stopRecognizeAudio = {},
            onLanguageSelect = {},
        )
    }
}
