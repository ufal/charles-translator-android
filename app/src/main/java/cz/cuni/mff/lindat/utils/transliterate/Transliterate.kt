package cz.cuni.mff.lindat.utils.transliterate

/**
 * @author Tomas Krabac
 */
object Transliterate {

    fun transliterateCyrilToLatin(text: String): String {
        val result = StringBuilder()

        for (i in text.indices) {
            if (cyrilToLatinMap.contains(text[i])) {
                var candidate = cyrilToLatinMap[text[i]] ?: "";
                if (
                    candidate.length > 1 &&
                    i < text.length - 1 &&
                    text[i + 1].isLowerCase()
                ) {
                    candidate = candidate[0] + candidate.substring(1).lowercase();
                }
                result.append(candidate)
            } else {
                result.append(text[i])
            }
        }
        return result.toString()
    }

    fun transliterateLatinToCyril(text: String): String {
        val result = StringBuilder()

        var i = 0
        while (i < text.length) {
            var bigram = text.substring(i, minOf(text.length, i + 2))
            if (bigram[0].isUpperCase()) {
                bigram = bigram.uppercase();
            }

            when {
                latinToCyrilDoubleMap.contains(bigram) -> {
                    result.append(latinToCyrilDoubleMap[bigram] ?: "")
                    i++
                }
                latinToCyrilSingleTable.contains(text[i].toString()) -> {
                    result.append(latinToCyrilSingleTable[text[i].toString()] ?: "")
                }
                else -> {
                    result.append(text[i])
                }
            }
            i++
        }

        return result.toString()
    }

    private val cyrilToLatinMap = hashMapOf(
        'А' to "A",
        'Б' to "B",
        'В' to "V",
        'Г' to "H",
        'Д' to "D",
        'Е' to "E",
        'Ё' to "Ë",
        'Ж' to "Ž",
        'З' to "Z",
        'И' to "Y",
        'Й' to "J",
        'К' to "K",
        'Л' to "L",
        'М' to "M",
        'Н' to "N",
        'О' to "O",
        'П' to "P",
        'Р' to "R",
        'С' to "S",
        'Т' to "T",
        'У' to "U",
        'Ф' to "F",
        'Х' to "CH",
        'Ц' to "C",
        'Ч' to "Č",
        'Ш' to "Š",
        'Щ' to "ŠČ",
        'Ъ' to "'",
        'Ы' to "Y",
        'Ь' to "'",
        'Э' to "È",
        'Ю' to "JU",
        'Я' to "JA",
        'а' to "a",
        'б' to "b",
        'в' to "v",
        'г' to "h",
        'д' to "d",
        'е' to "e",
        'ё' to "ë",
        'ж' to "ž",
        'з' to "z",
        'и' to "y",
        'й' to "j",
        'к' to "k",
        'л' to "l",
        'м' to "m",
        'н' to "n",
        'о' to "o",
        'п' to "p",
        'р' to "r",
        'с' to "s",
        'т' to "t",
        'у' to "u",
        'ф' to "f",
        'х' to "ch",
        'ц' to "c",
        'ч' to "č",
        'ш' to "š",
        'щ' to "šč",
        'ъ' to "'",
        'ы' to "y",
        'ь' to "'",
        'э' to "è",
        'ю' to "ju",
        'я' to "ja",
        'Є' to "JE",
        'І' to "I",
        'Ї' to "JI",
        'Ґ' to "G",
        'є' to "je",
        'і' to "i",
        'ї' to "ji",
        'ґ' to "g",
        'Ў' to "W",
        'ў' to "w",
        'Ђ' to "Đ",
        'Ј' to "J",
        'Љ' to "LJ",
        'Њ' to "NJ",
        'Ћ' to "Ć",
        'Џ' to "DŽ",
        'ђ' to "đ",
        'ј' to "j",
        'љ' to "lj",
        'њ' to "nj",
        'ћ' to "ć",
        'џ' to "dž",
        'Ѓ' to "Ď",
        'Ѕ' to "DZ",
        'Ќ' to "Ť",
        'ѓ' to "ď",
        'ѕ' to "dz",
        'ќ' to "ť",
    )

    private val latinToCyrilDoubleMap = hashMapOf(
        "CH" to "Х",
        "ŠČ" to "Щ",
        "JU" to "Ю",
        "JA" to "Я",
        "ch" to "х",
        "šč" to "щ",
        "ju" to "ю",
        "ja" to "я",
        "JE" to "Є",
        "JI" to "Ї",
        "je" to "є",
        "ji" to "ї",
        "LJ" to "Љ",
        "NJ" to "Њ",
        "DŽ" to "Џ",
        "lj" to "љ",
        "nj" to "њ",
        "dž" to "џ",
        "DZ" to "Ѕ",
        "dz" to "ѕ",
    )

    private val latinToCyrilSingleTable = hashMapOf(
        "A" to "А",
        "B" to "Б",
        "V" to "В",
        "H" to "Г",
        "D" to "Д",
        "E" to "Е",
        "Ë" to "Ё",
        "Ž" to "Ж",
        "Z" to "З",
        "Y" to "И",
        "J" to "Й",
        "K" to "К",
        "L" to "Л",
        "M" to "М",
        "N" to "Н",
        "O" to "О",
        "P" to "П",
        "R" to "Р",
        "S" to "С",
        "T" to "Т",
        "U" to "У",
        "F" to "Ф",
        "C" to "Ц",
        "Č" to "Ч",
        "Š" to "Ш",
        "È" to "Э",
        "a" to "а",
        "b" to "б",
        "v" to "в",
        "h" to "г",
        "d" to "д",
        "e" to "е",
        "ë" to "ё",
        "ž" to "ж",
        "z" to "з",
        "y" to "и",
        "j" to "й",
        "k" to "к",
        "l" to "л",
        "m" to "м",
        "n" to "н",
        "o" to "о",
        "p" to "п",
        "r" to "р",
        "s" to "с",
        "t" to "т",
        "u" to "у",
        "f" to "ф",
        "c" to "ц",
        "č" to "ч",
        "š" to "ш",
        "è" to "э",
        "I" to "І",
        "G" to "Ґ",
        "i" to "і",
        "g" to "ґ",
        "W" to "Ў",
        "w" to "ў",
        "Đ" to "Ђ",
        "J" to "Ј",
        "Ć" to "Ћ",
        "đ" to "ђ",
        "ć" to "ћ",
        "Ď" to "Ѓ",
        "Ť" to "Ќ",
        "ď" to "ѓ",
        "ќ" to "ť",
        "ě" to "є",
        "Ě" to "Є",
        "Q" to "KB",
        "q" to "кв",
        "Ř" to "РЖ",
        "ř" to "рж",
    )


}