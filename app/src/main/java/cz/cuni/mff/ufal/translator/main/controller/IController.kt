package cz.cuni.mff.ufal.translator.main.controller

import cz.cuni.mff.ufal.translator.history.data.HistoryItem

/**
 * @author Tomas Krabac
 */
interface IController {

    fun navigateHistory()

    fun navigateMainScreen(item: HistoryItem)

    fun onBackPressed()
}