package cz.cuni.mff.ufal.translator.main.controller

import cz.cuni.mff.ufal.translator.interactors.preferences.data.DarkModeSetting
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Tomas Krabac
 */
interface IMainController {

    val darkModeSetting: StateFlow<DarkModeSetting>

    fun navigateHistory()

    fun navigateAboutScreen()

    fun navigateSettingsScreen()

    fun navigateConversationScreen()

    fun openWebUrl(url: String)

    fun sendMail(mail: String)

    fun onBackPressed()

    fun setDarkModeSettings(darkModeSetting: DarkModeSetting)
}