package cz.cuni.mff.ufal.translator.ui.translations.models

import androidx.compose.runtime.Immutable

/**
 * @author Tomas Krabac
 */
@Immutable
data class InputTextData(
    val text: String = "",
    val source: TextSource = TextSource.ClearButton
)