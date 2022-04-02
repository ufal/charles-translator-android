package cz.cuni.mff.lindat.main.controller

import cz.cuni.mff.lindat.history.data.HistoryItem

/**
 * @author Tomas Krabac
 */
interface IController {

    fun navigateHistory()

    fun navigateMainScreen(item: HistoryItem)

    fun onBackPressed()
}