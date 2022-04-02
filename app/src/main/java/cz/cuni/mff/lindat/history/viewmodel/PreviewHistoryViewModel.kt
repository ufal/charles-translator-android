package cz.cuni.mff.lindat.history.viewmodel

import cz.cuni.mff.lindat.history.data.HistoryItem
import cz.cuni.mff.lindat.main.viewmodel.Language
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @author Tomas Krabac
 */
class PreviewHistoryViewModel : IHistoryViewModel {

    override fun deleteItem(item: HistoryItem) {}
    override fun updateItem(item: HistoryItem) {}

    override val historyItems = MutableStateFlow(
        listOf(
            HistoryItem("test", Language.Czech, Language.Ukrainian, false),
            HistoryItem("hrozně moc dlouhý text, ze se ani na jednu radku nevejde", Language.Czech, Language.Ukrainian, false),
            HistoryItem("oblibena", Language.Czech, Language.Ukrainian, true),
            HistoryItem("test 4", Language.Czech, Language.Ukrainian, false),
            HistoryItem("test 5", Language.Czech, Language.Ukrainian, false),
            HistoryItem("test 6", Language.Czech, Language.Ukrainian, false),
            HistoryItem("z UK do CS", Language.Ukrainian, Language.Czech, false),
        )
    )
}