package cz.cuni.mff.ufal.translator.ui.conversation.models

import cz.cuni.mff.ufal.translator.ui.translations.models.Language
import cz.cuni.mff.ufal.translator.ui.translations.models.OutputTextData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Tomas Krabac
 */
data class ConversationModel(
    val text: MutableStateFlow<OutputTextData>,
    val language: Language,
    val position: BubblePosition,
) {
}