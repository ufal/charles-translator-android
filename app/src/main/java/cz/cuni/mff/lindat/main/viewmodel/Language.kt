package cz.cuni.mff.lindat.main.viewmodel

import java.util.*

/**
 * @author Tomas Krabac
 */
enum class Language(val code: String, val locale: Locale) {
    Czech("cs", Locale("cs")),
    Ukrainian("uk", Locale("ru_UA"))
}