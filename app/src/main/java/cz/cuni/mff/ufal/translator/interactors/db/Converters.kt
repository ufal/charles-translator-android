package cz.cuni.mff.ufal.translator.interactors.db

import androidx.room.TypeConverter
import cz.cuni.mff.ufal.translator.interactors.languages.LanguagesManager
import cz.cuni.mff.ufal.translator.ui.translations.models.Language

/**
 * @author Tomas Krabac
 */
class Converters {

    @TypeConverter
    fun toLanguage(code: String) = LanguagesManager.getLanguage(code) ?: error("unsupported code $code")


    @TypeConverter
    fun fromLanguage(value: Language) = value.code
}