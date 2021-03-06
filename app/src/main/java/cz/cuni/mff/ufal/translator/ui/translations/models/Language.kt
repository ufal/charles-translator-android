package cz.cuni.mff.ufal.translator.ui.translations.models

import java.util.*

/**
 * @author Tomas Krabac
 */
enum class Language(val code: String, val locale: Locale) {
    Czech("cs", Locale("cs_CZ")),
    Ukrainian("uk", Locale("uk_UA"))
}