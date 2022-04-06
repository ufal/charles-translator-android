package cz.cuni.mff.ufal.translator.db

import androidx.room.TypeConverter
import cz.cuni.mff.ufal.translator.main.viewmodel.Language

/**
 * @author Tomas Krabac
 */
class Converters {

    @TypeConverter
    fun toLanguage(value: String) = when (value) {
        Language.Czech.code -> Language.Czech
        Language.Ukrainian.code -> Language.Ukrainian
        else -> error("unsupported enum $value")
    }


    @TypeConverter
    fun fromLanguage(value: Language) = value.code
}