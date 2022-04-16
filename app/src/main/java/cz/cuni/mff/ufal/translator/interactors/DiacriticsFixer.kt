package cz.cuni.mff.ufal.translator.interactors

/**
 * @author Tomas Krabac
 */
object DiacriticsFixer {

    private val diacriticsMap = hashMapOf(
        "Eˇ" to "Ě",
        "Sˇ" to "Š",
        "Cˇ" to "Č",
        "Rˇ" to "Ř",
        "Zˇ" to "Ž",
        "Y´" to "Ý",
        "A´" to "Á",
        "I´" to "Í",
        "E´" to "É",
        "U´" to "Ú",
        "U˚" to "Ů",
        "Dˇ" to "Ď",
        "Tˇ" to "Ť",
        "Nˇ" to "Ň",

        "eˇ" to "ě",
        "sˇ" to "š",
        "cˇ" to "č",
        "rˇ" to "ř",
        "zˇ" to "ž",
        "y´" to "ý",
        "a´" to "á",
        "i´" to "í",
        "e´" to "é",
        "u´" to "ú",
        "u˚" to "ů",
        "dˇ" to "ď",
        "tˇ" to "ť",
    )

    fun fixDiacritic(text: String) : String {
        var result = text
        diacriticsMap.entries.forEach{ entry ->
            result = result.replace(entry.key, entry.value)
        }
        return result
    }

}