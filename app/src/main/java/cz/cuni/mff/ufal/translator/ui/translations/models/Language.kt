package cz.cuni.mff.ufal.translator.ui.translations.models

import java.util.*

/**
 * @author Tomas Krabac
 */
enum class Language(val code: String, val locale: Locale) {
    Czech("cs", Locale("cs-CZ")),
    Ukrainian("uk", Locale("ru_UA"))
}