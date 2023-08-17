package cz.cuni.mff.ufal.translator.interactors.languages

import cz.cuni.mff.ufal.translator.ui.translations.models.Language
import javax.inject.Inject

class LanguagesManager @Inject constructor(

) : ILanguagesManager {

    override val defaultInputLanguage = Language.Czech

    override val defaultOutputLanguage = Language.Ukrainian

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
}