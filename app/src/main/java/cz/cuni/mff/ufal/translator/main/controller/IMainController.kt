package cz.cuni.mff.ufal.translator.main.controller

import kotlinx.coroutines.flow.StateFlow

/**
 * @author Tomas Krabac
 */
interface IMainController {

    val isDarkMode: StateFlow<Boolean>

    fun navigateHistory()

    fun navigateAboutScreen()

    fun navigateSettingsScreen()

    fun openWebUrl(url: String)

    fun sendMail(mail: String)

    fun onBackPressed()

    fun setDarkMode(isDarkMode: Boolean)
}