package cz.cuni.mff.lindat.main.controller

import cz.cuni.mff.lindat.history.data.HistoryItem

/**
 * @author Tomas Krabac
 */
class PreviewIController : IController {
    override fun navigateHistory() {}
    override fun navigateMainScreen(item: HistoryItem) {}
    override fun onBackPressed() {}
}