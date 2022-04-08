package cz.cuni.mff.ufal.translator.ui.history.viewmodel

import cz.cuni.mff.ufal.translator.ui.history.model.HistoryItem
import cz.cuni.mff.ufal.translator.ui.translations.models.Language
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Tomas Krabac
 */
class PreviewHistoryViewModel : IHistoryViewModel {

    private val items = listOf(
        HistoryItem("test", "preklad", Language.Czech, Language.Ukrainian, false),
        HistoryItem(
            "hrozně moc dlouhý text, ze se ani na jednu radku nevejde",
            "hrozně moc dlouhý text, ze se ani na jednu radku nevejde",
            Language.Czech,
            Language.Ukrainian,
            false
        ),
        HistoryItem("oblibena", "preklad", Language.Czech, Language.Ukrainian, true),
        HistoryItem("test 4", "preklad", Language.Czech, Language.Ukrainian, false),
        HistoryItem("test 5", "preklad", Language.Czech, Language.Ukrainian, false),
        HistoryItem("test 6", "preklad", Language.Czech, Language.Ukrainian, false),
        HistoryItem("z UK do CS", "preklad", Language.Ukrainian, Language.Czech, false),
    )

    override val allItems = MutableStateFlow(items)
    override val favouritesItems = MutableStateFlow(items)

    override val isTextToSpeechAvailable: StateFlow<Boolean>
        get() = TODO("Not yet implemented")

    override fun deleteItem(item: HistoryItem) {}
    override fun copyToClipBoard(text: String) {}
    override fun textToSpeech(item: HistoryItem) {}
    override fun updateItem(item: HistoryItem) {}

}