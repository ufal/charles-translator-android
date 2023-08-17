package cz.cuni.mff.ufal.translator.interactors.languages

import cz.cuni.mff.ufal.translator.ui.translations.models.Language

interface ILanguagesManager {

    val defaultInputLanguage: Language

    val defaultOutputLanguage: Language

    val supportedLanguages: List<Language>

    val supportedConversationLanguages: List<Language>

    fun getSupportedTranslations(inputLanguage: Language): List<Language>
}