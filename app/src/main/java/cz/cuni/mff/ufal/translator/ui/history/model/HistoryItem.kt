package cz.cuni.mff.ufal.translator.ui.history.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import cz.cuni.mff.ufal.translator.interactors.Transliterate.transliterateCyrilToLatin
import cz.cuni.mff.ufal.translator.interactors.Transliterate.transliterateLatinToCyril
import cz.cuni.mff.ufal.translator.ui.translations.models.Language
import kotlinx.parcelize.Parcelize

/**
 * @author Tomas Krabac
 */
@Entity(tableName = "history_items", primaryKeys = ["input_text", "input_language", "output_language"])
@Parcelize
data class HistoryItem(
    @ColumnInfo(name = "input_text") val inputText: String,
    @ColumnInfo(name = "output_text") val outputText: String,
    @ColumnInfo(name = "input_language") val inputLanguage: Language,
    @ColumnInfo(name = "output_language") val outputLanguage: Language,
    @ColumnInfo(name = "is_favourite") val isFavourite: Boolean = false,
    @ColumnInfo(name = "inserted_ms") val insertedMS: Long = System.currentTimeMillis(),
) : Parcelable {

    val secondaryText: String
        get() {
            return when (outputLanguage) {
                Language.Czech -> {
                    transliterateLatinToCyril(outputText)
                }
                Language.Ukrainian -> {
                    transliterateCyrilToLatin(outputText)
                }
                else -> ""
            }
        }
}