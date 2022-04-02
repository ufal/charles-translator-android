package cz.cuni.mff.lindat.history.data

import cz.cuni.mff.lindat.db.history.HistoryItemDB
import cz.cuni.mff.lindat.main.viewmodel.Language

/**
 * @author Tomas Krabac
 */
data class HistoryItem(
    val text: String,
    val inputLanguage: Language,
    val outputLanguage: Language,
    val isFavourite: Boolean,
) {

    constructor(item: HistoryItemDB) : this(
        text = item.text,
        inputLanguage = item.inputLanguage,
        outputLanguage = item.outputLanguage,
        isFavourite = item.isFavourite,
    )
}