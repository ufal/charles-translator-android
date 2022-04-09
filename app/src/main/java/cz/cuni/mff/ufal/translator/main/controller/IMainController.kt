package cz.cuni.mff.ufal.translator.main.controller

import cz.cuni.mff.ufal.translator.ui.history.model.HistoryItem

/**
 * @author Tomas Krabac
 */
interface IMainController {

    fun navigateHistory()

    fun navigateMainScreen(item: HistoryItem)

    fun navigateAboutScreen()

    fun navigateSettingsScreen()

    fun openWebUrl(url: String)

    fun sendMail(mail: String)

    fun onBackPressed()
}