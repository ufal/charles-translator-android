package cz.cuni.mff.lindat.api

import cz.cuni.mff.lindat.main.viewmodel.Language

/**
 * @author Tomas Krabac
 */
interface IApi {

    suspend fun translate(inputLanguage: Language, outputLanguage: Language, text: String): Result<String>
}