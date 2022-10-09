package cz.cuni.mff.ufal.translator.ui.translations.viewmodel

import cz.cuni.mff.ufal.translator.interactors.tts.TextToSpeechError
import cz.cuni.mff.ufal.translator.ui.history.model.HistoryItem
import cz.cuni.mff.ufal.translator.ui.translations.models.TranslationsScreenState
import cz.cuni.mff.ufal.translator.ui.translations.models.InputTextData
import cz.cuni.mff.ufal.translator.ui.translations.models.Language
import cz.cuni.mff.ufal.translator.ui.translations.models.OutputTextData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow

/**
 * @author Tomas Krabac
 */
class PreviewTranslationsViewModel : ITranslationsViewModel {
    override val inputTextData = MutableStateFlow(InputTextData("zdrojovy text"))
    override val outputTextData = MutableStateFlow(OutputTextData("hlavni text", "druhy text"))
    override val inputLanguage = MutableStateFlow(Language.Czech)
    override val outputLanguage = MutableStateFlow(Language.Ukrainian)
    override val state = MutableStateFlow(TranslationsScreenState.Success)
    override val hasFinishedOnboarding = MutableStateFlow(true)
    override val textToSpeechErrors = emptyFlow<TextToSpeechError>()
    override val isSpeechRecognizerAvailable = true
    override val isListening = MutableStateFlow(false)
    override val rmsdB = MutableStateFlow(0.0f)

    override fun setInputText(data: InputTextData) {}
    override fun swapLanguages() {}
    override fun copyToClipBoard(text: String) {}
    override fun pasteFromClipBoard() {}
    override fun setFromHistoryItem(item: HistoryItem) {}
    override fun retry() {}
    override fun setFinishedOnboarding(agreeWithDataCollection: Boolean) {}
    override fun textToSpeech() {}
    override fun startRecognizeAudio() {}
    override fun stopRecognizeAudio() {}
}