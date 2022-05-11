package cz.cuni.mff.ufal.translator.interactors.analytics.events

import cz.cuni.mff.ufal.translator.ui.translations.models.Language


/**
 * @author Tomas Krabac
 */
data class SpeechToTextEvent(
    val language: Language,
    val text: String,
)