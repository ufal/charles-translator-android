package cz.cuni.mff.ufal.translator.interactors.languages

import cz.cuni.mff.ufal.translator.ui.translations.models.Language

interface ILanguagesManager {

    val lastInputLanguage: Language

    val lastOutputLanguage: Language

    val supportedLanguages: List<Language>

    val supportedConversationLanguages: List<Language>

    fun getSupportedTranslations(inputLanguage: Language): List<Language>
}