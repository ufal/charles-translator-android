package cz.cuni.mff.ufal.translator.main.controller

import cz.cuni.mff.ufal.translator.history.data.HistoryItem

/**
 * @author Tomas Krabac
 */
class PreviewIController : IController {
    override fun navigateHistory() {}
    override fun navigateMainScreen(item: HistoryItem) {}
    override fun onBackPressed() {}
}