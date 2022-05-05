package cz.cuni.mff.ufal.translator.interactors.analytics.events

import cz.cuni.mff.ufal.translator.ui.translations.models.InputTextData
import cz.cuni.mff.ufal.translator.ui.translations.models.Language


/**
 * @author Tomas Krabac
 */
data class TranslateEvent(
    val inputLanguage: Language,
    val outputLanguage: Language,
    val data: InputTextData,
)