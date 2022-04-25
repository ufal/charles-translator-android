package cz.cuni.mff.ufal.translator.main.controller

/**
 * @author Tomas Krabac
 */
interface IMainController {

    val isDarkMode: Boolean

    fun navigateHistory()

    fun navigateAboutScreen()

    fun navigateSettingsScreen()

    fun openWebUrl(url: String)

    fun sendMail(mail: String)

    fun onBackPressed()
}