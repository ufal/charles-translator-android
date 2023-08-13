package cz.cuni.mff.ufal.translator.interactors.db

import androidx.room.TypeConverter
import cz.cuni.mff.ufal.translator.ui.translations.models.Language

/**
 * @author Tomas Krabac
 */
class Converters {

    @TypeConverter
    fun toLanguage(value: String) = when (value) {
        Language.Czech.code -> Language.Czech
        Language.Ukrainian.code -> Language.Ukrainian
        Language.English.code -> Language.English
        Language.French.code -> Language.French
        Language.Polish.code -> Language.Polish
        Language.Russian.code -> Language.Russian
        else -> error("unsupported enum $value")
    }

    @TypeConverter
    fun fromLanguage(value: Language) = value.code
}