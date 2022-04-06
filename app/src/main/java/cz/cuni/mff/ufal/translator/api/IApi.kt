package cz.cuni.mff.ufal.translator.api

import cz.cuni.mff.ufal.translator.main.viewmodel.Language

/**
 * @author Tomas Krabac
 */
interface IApi {

    suspend fun translate(inputLanguage: Language, outputLanguage: Language, text: String): Result<String>
}