package cz.cuni.mff.ufal.translator.ui.translations.models

import androidx.annotation.StringRes
import cz.cuni.mff.ufal.translator.R
import java.util.Locale

/**
 * @author Tomas Krabac
 */
enum class Language(
    val code: String,
    val bcp47Code: String,
    @StringRes
    val labelRes: Int,
) {
    Czech(
        code = "cs",
        bcp47Code = "cs-CZ",
        labelRes = R.string.cz_label,
    ),
    Ukrainian(
        code = "uk",
        bcp47Code = "uk-UA",
        labelRes = R.string.uk_label,
    ),
    English(
        code = "en",
        bcp47Code = "en-US",
        labelRes = R.string.en_label,
    ),
    French(
        code = "fr",
        bcp47Code = "fr-FR",
        labelRes = R.string.fr_label,
    ),
    Polish(
        code = "pl",
        bcp47Code = "pl-PL",
        labelRes = R.string.pl_label,
    ),
    Russian(
        code = "ru",
        bcp47Code = "ru-RU",
        labelRes = R.string.ru_label,
    );

    val locale = Locale(
        /* language = */ bcp47Code.substringBefore("-"),
        /* country = */ bcp47Code.substringAfter("-")
    )
}