package cz.cuni.mff.lindat

import cz.cuni.mff.lindat.utils.transliterate.Transliterate.transliterateCyrilToLatin
import cz.cuni.mff.lindat.utils.transliterate.Transliterate.transliterateLatinToCyril
import org.junit.Assert.assertEquals
import org.junit.Test

class TransliterateTest {
    @Test
    fun transliterateCyrilToLatinTest() {
        cyrilToLatinTest(
            input = "",
            expected = ""
        )

        cyrilToLatinTest(
            input = "Переговори між Україною та Росією в суботу, за словами українського переговорника Давида Арахамії, перейшли на стадію, щоб їх могли обговорити президенти двох країн Володимир Зеленський і Володимир Путін. За словами Арахамії, Росія приймає українську позицію, за винятком позиції щодо питання Криму. Про це глава українських переговорників заявив у суботу, передає агентство \"Інтерфакс-Україна\".",
            expected = "Perehovory miž Ukrajinoju ta Rosijeju v subotu, za slovamy ukrajins'koho perehovornyka Davyda Arachamiji, perejšly na stadiju, ščob jich mohly obhovoryty prezydenty dvoch krajin Volodymyr Zelens'kyj i Volodymyr Putin. Za slovamy Arachamiji, Rosija pryjmaje ukrajins'ku pozyciju, za vynjatkom pozyciji ščodo pytannja Krymu. Pro ce hlava ukrajins'kych perehovornykiv zajavyv u subotu, peredaje ahentstvo \"Interfaks-Ukrajina\"."
        )

        cyrilToLatinTest(
            input = "За його словами, місцем зустрічі Путіна і Зеленського, ймовірно, була б Туреччина. За його словами, дата і місце такої зустрічі поки невідомі. «Дата і місце не відомі, але ми вважаємо, що це, швидше за все, буде Стамбул або Анкара», - сказав Арахамія.\n" +
                    "За словами Арахамії, президент Туреччини Реджеп Таїп Ердоган у п'ятницю провів телефонну розмову з Путіним і Зеленським, очевидно підтвердивши готовність \"провести зустріч у найближчому майбутньому\".\n" +
                    "Представник адміністрації президента України Михайло Подоляк заявив турецькому телеканалу НТВ за підсумками переговорів у Стамбулі, що зустріч українського та російського президентів може відбутися за два-три тижні.",
            expected = "Za joho slovamy, miscem zustriči Putina i Zelens'koho, jmovirno, bula b Tureččyna. Za joho slovamy, data i misce takoji zustriči poky nevidomi. «Data i misce ne vidomi, ale my vvažajemo, ščo ce, švydše za vse, bude Stambul abo Ankara», - skazav Arachamija.\n" +
                    "Za slovamy Arachamiji, prezydent Tureččyny Redžep Tajip Erdohan u p'jatnycju proviv telefonnu rozmovu z Putinym i Zelens'kym, očevydno pidtverdyvšy hotovnist' \"provesty zustrič u najblyžčomu majbutn'omu\".\n" +
                    "Predstavnyk administraciji prezydenta Ukrajiny Mychajlo Podoljak zajavyv turec'komu telekanalu NTV za pidsumkamy perehovoriv u Stambuli, ščo zustrič ukrajins'koho ta rosijs'koho prezydentiv može vidbutysja za dva-try tyžni."
        )
    }

    @Test
    fun transliterateLatinToCyrilTest() {
        latinToCyrilTest(
            input = "",
            expected = ""
        )

        latinToCyrilTest(
            input = "Jednání mezi Ukrajinou a Ruskem se v sobotu podle ukrajinského vyjednávače Davida Arahamije posunula do fáze, aby je mohli projednat prezidenti obou zemí Vladimir Zelenskyj a Vladimir Putin. Podle Arahamije Rusko zaujímá ukrajinské stanovisko s výjimkou postoje k otázce Krymu. Šéf ukrajinských vyjednávačů to v sobotu řekl agentuře Interfax-Ukrajina.",
            expected = "Єднáнí мезі Україноу а Рускем се в соботу подле українскéго виєднáваче Давіда Арагаміє посунула до фáзе, аби є моглі проєднат презіденті обоу земí Владімір Зеленский а Владімір Путін. Подле Арагаміє Руско зауйíмá українскé становіско с вýїмкоу постоє к отáзце Криму. Шéф українскýх виєднáвачů то в соботу ржекл аґентурже Інтерфаx-Україна."
        )

        latinToCyrilTest(
            input = "Místem setkání Putina a Zelenského by podle něj pravděpodobně bylo Turecko. Datum a místo tohoto setkání podle něj zatím nejsou známy. „Datum ani místo nejsou známy, ale domníváme se, že to bude s největší pravděpodobností Istanbul nebo Ankara,“ řekl Arahamija.\n" +
                    "Podle Arahamije turecký prezident Recep Tayyip Erdogan v pátek telefonicky hovořil s Putinem a Zelenským a zjevně potvrdil ochotu „uspořádat setkání v blízké budoucnosti“.\n" +
                    "Mluvčí prezidentské kanceláře Mychajlo Podolák řekl turecké televizi NTV na základě výsledků jednání v Istanbulu, že setkání ukrajinského a ruského prezidenta by se mohlo uskutečnit za dva až tři týdny.",
            expected = "Мíстем сеткáнí Путіна а Зеленскéго би подле нєй правдєподобнє било Турецко. Датум а мíсто тогото сеткáнí подле нєй затíм нейсоу знáми. „Датум ані мíсто нейсоу знáми, але домнíвáме се, же то буде с нейвєтшí правдєподобностí Істанбул небо Анкара,“ ржекл Арагамія.\n" +
                    "Подле Арагаміє турецкý презідент Рецеп Таииіп Ердоґан в пáтек телефоніцки говоржіл с Путінем а Зеленскýм а зєвнє потврділ охоту „успоржáдат сеткáнí в блíзкé будоуцності“.\n" +
                    "Млувчí презідентскé канцелáрже Михайло Подолáк ржекл турецкé телевізі НТВ на зáкладє вýследкů єднáнí в Істанбулу, же сеткáнí українскéго а рускéго презідента би се могло ускутечніт за два аж тржі тýдни.",
        )
    }

    private fun cyrilToLatinTest(input: String, expected: String) {
        assertEquals(expected, transliterateCyrilToLatin(input))
    }

    private fun latinToCyrilTest(input: String, expected: String) {
        assertEquals(expected, transliterateLatinToCyril(input))
    }
}