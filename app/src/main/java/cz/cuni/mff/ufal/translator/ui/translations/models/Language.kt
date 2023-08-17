package cz.cuni.mff.ufal.translator.ui.translations.models

import java.util.*

/**
 * @author Tomas Krabac
 */
enum class Language(val code: String, val locale: Locale) {
    Czech("cs", Locale("cs_CZ")),
    Ukrainian("uk", Locale("uk_UA")),
    English("en", Locale("en-us")),
    French("fr", Locale("fr-fr")),
    Polish("pl", Locale("pl")),
    Russian("ru", Locale("ru")),
}