package cz.cuni.mff.ufal.translator.main.controller

import cz.cuni.mff.ufal.translator.ui.history.model.HistoryItem

/**
 * @author Tomas Krabac
 */
class PreviewIController : IController {
    override fun navigateHistory() {}
    override fun navigateMainScreen(item: HistoryItem) {}
    override fun navigateAboutScreen() {}
    override fun openWebUrl(url: String) {}
    override fun sendMail(mail: String) {}
    override fun onBackPressed() {}
}