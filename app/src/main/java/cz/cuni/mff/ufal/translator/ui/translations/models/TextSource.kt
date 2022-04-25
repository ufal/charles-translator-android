package cz.cuni.mff.ufal.translator.ui.translations.models

/**
 * @author Tomas Krabac
 */
enum class TextSource(val api: String) {
    Keyboard("keyboard"),
    Voice("voice"),
    Clipboard("clipboard"),
    ClearButton("keyboard"),
    History("history"),
    SwapLanguages("swap-languages"),
    ClearVoice("keyboard"),
}