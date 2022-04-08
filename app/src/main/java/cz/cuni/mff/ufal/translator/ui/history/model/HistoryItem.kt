package cz.cuni.mff.ufal.translator.ui.history.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import cz.cuni.mff.ufal.translator.interactors.Transliterate.transliterateCyrilToLatin
import cz.cuni.mff.ufal.translator.interactors.Transliterate.transliterateLatinToCyril
import cz.cuni.mff.ufal.translator.ui.translations.models.Language
import kotlinx.serialization.Serializable

/**
 * @author Tomas Krabac
 */
@Entity(tableName = "history_items", primaryKeys = ["input_text", "output_text", "input_language", "output_language"])
@Serializable
data class HistoryItem(
    @ColumnInfo(name = "input_text") val inputText: String,
    @ColumnInfo(name = "output_text") val outputText: String,
    @ColumnInfo(name = "input_language") val inputLanguage: Language,
    @ColumnInfo(name = "output_language") val outputLanguage: Language,
    @ColumnInfo(name = "is_favourite") val isFavourite: Boolean = false,
    @ColumnInfo(name = "inserted_ms") val insertedMS: Long = System.currentTimeMillis(),
) {
    val secondaryText: String
        get() {
            return if (outputLanguage == Language.Czech) {
                transliterateLatinToCyril(outputText)
            } else {
                transliterateCyrilToLatin(outputText)
            }
        }
}