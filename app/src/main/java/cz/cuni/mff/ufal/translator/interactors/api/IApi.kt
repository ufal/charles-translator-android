package cz.cuni.mff.ufal.translator.interactors.api

import cz.cuni.mff.ufal.translator.ui.translations.models.Language
import cz.cuni.mff.ufal.translator.ui.translations.models.TextSource

/**
 * @author Tomas Krabac
 */
interface IApi {

    suspend fun translate(
        inputLanguage: Language,
        outputLanguage: Language,
        text: String,
        textSource: TextSource,
    ): Result<String>
}