package cz.cuni.mff.ufal.translator.ui.conversation.viewmodel

import cz.cuni.mff.ufal.translator.ui.conversation.models.ConversationModel
import cz.cuni.mff.ufal.translator.ui.translations.models.Language
import cz.cuni.mff.ufal.translator.ui.translations.models.OutputTextData
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @author Tomas Krabac
 */
class PreviewConversationViewModel() : IConversationViewModel {

    override val isSpeechRecognizerAvailable = true

    override val isListening = MutableStateFlow(true)

    override val isOffline = MutableStateFlow(false)

    override val rmsdB = MutableStateFlow(1F)

    override val activeLanguage = MutableStateFlow(Language.Ukrainian)

    override val conversation = MutableStateFlow(
        listOf(
            ConversationModel(
                text = MutableStateFlow(
                    OutputTextData(
                        "Ahoj",
                        "Hello"
                    )
                ),
                language = Language.Czech
            ),
            ConversationModel(
                text = MutableStateFlow(
                    OutputTextData(
                        "Jak se máš?",
                        "How are you?"
                    )
                ),
                language = Language.Czech
            ),
            ConversationModel(
                text = MutableStateFlow(
                    OutputTextData(
                        "I am fine",
                        "Mám se dobře"
                    )
                ),
                language = Language.Ukrainian
            ),
            ConversationModel(
                text = MutableStateFlow(
                    OutputTextData(
                        "I am trying long text to wrap on more rows",
                        "Mám se dobře"
                    )
                ),
                language = Language.Ukrainian
            ),
        )
    )

    override fun startRecognizeAudio(language: Language) {}
    override fun stopRecognizeAudio() {}

}