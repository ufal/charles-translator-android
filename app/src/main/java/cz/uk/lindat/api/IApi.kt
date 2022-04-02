package cz.uk.lindat.api

import cz.uk.lindat.main.viewmodel.Language

/**
 * @author Tomas Krabac
 */
interface IApi {

    suspend fun translate(inputLanguage: Language, outputLanguage: Language, text: String): Result<String>
}