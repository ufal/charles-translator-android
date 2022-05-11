package cz.cuni.mff.ufal.translator.ui.translations.models

import androidx.compose.runtime.Immutable

/**
 * @author Tomas Krabac
 */
@Immutable
data class OutputTextData(
    val mainText: String = "",
    val secondaryText: String = "",
)