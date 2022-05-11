package cz.cuni.mff.ufal.translator.interactors.analytics.events

import cz.cuni.mff.ufal.translator.interactors.crashlytics.Screen
import cz.cuni.mff.ufal.translator.ui.translations.models.Language


/**
 * @author Tomas Krabac
 */
data class TextToSpeechEvent(
    val language: Language,
    val text: String,
    val screen: Screen,
)