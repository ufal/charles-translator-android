package cz.cuni.mff.ufal.translator.interactors.languages

import cz.cuni.mff.ufal.translator.interactors.preferences.IUserDataStore
import cz.cuni.mff.ufal.translator.ui.translations.models.Language
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class LanguagesManager @Inject constructor(
userDataStore: IUserDataStore,
) : ILanguagesManager {

    override val lastInputLanguage = runBlocking {
        userDataStore.lastInputLanguage.firstOrNull() ?: DEFAULT_INPUT_LANGUAGE
    }

    override val lastOutputLanguage = runBlocking {
        userDataStore.lastOutputLanguage.firstOrNull() ?: DEFAULT_OUTPUT_LANGUAGE
    }

    override val supportedLanguages: List<Language>
        get() = listOf(
            Language.Czech,
            Language.English,
            Language.French,
            Language.Polish,
            Language.Ukrainian,
            Language.Russian,
        )

    override val supportedConversationLanguages: List<Language>
        get() = listOf(
            Language.English,
            Language.French,
            Language.Ukrainian,
            Language.Russian,
        )

    // TODO: nacitat z api
    override fun getSupportedTranslations(inputLanguage: Language): List<Language> {
        return when (inputLanguage) {
            Language.Czech -> listOf(
                Language.English,
                Language.Ukrainian,
                Language.French,
                Language.Russian,
            )

            Language.English -> listOf(
                Language.Polish,
                Language.Czech,
                Language.French,
            )

            Language.French -> listOf(
                Language.Czech,
                Language.English,
            )

            Language.Polish -> listOf(
                Language.English,
            )

            Language.Ukrainian -> listOf(
                Language.Czech,
            )

            Language.Russian -> listOf(
                Language.Czech,
            )
        }
    }

    companion object {

        val DEFAULT_INPUT_LANGUAGE = Language.Czech
        val DEFAULT_OUTPUT_LANGUAGE = Language.Ukrainian

        fun getLanguage(code: String): Language? {
            return when (code) {
                Language.Czech.code -> Language.Czech
                Language.Ukrainian.code -> Language.Ukrainian
                Language.English.code -> Language.English
                Language.French.code -> Language.French
                Language.Polish.code -> Language.Polish
                Language.Russian.code -> Language.Russian
                else -> null
            }
        }
    }
}