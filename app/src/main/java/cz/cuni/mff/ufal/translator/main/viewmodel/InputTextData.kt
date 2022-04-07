package cz.cuni.mff.ufal.translator.main.viewmodel

/**
 * @author Tomas Krabac
 */
data class InputTextData(
    val text: String = "",
    val source: TextSource = TextSource.CleanButton
)