package cz.cuni.mff.ufal.translator.ui.translations.models

import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import cz.cuni.mff.ufal.translator.R
import java.util.*

/**
 * @author Tomas Krabac
 */
enum class Language(
    val code: String,
    val locale: Locale,
    @StringRes
    val labelRes: Int,
) {
    Czech(
        code = "cs",
        locale = Locale("cs_CZ"),
        labelRes = R.string.cz_label,
    ),
    Ukrainian(
        code = "uk",
        locale = Locale("uk_UA"),
        labelRes = R.string.uk_label,
    ),
    English(
        code = "en",
        locale = Locale("en-us"),
        labelRes = R.string.en_label,
    ),
    French(
        code = "fr",
        locale = Locale("fr-fr"),
        labelRes = R.string.fr_label,
    ),
    Polish(
        code = "pl",
        locale = Locale("pl"),
        labelRes = R.string.pl_label,
    ),
    Russian(
        code = "ru",
        locale = Locale("ru"),
        labelRes = R.string.ru_label,
    ),
}