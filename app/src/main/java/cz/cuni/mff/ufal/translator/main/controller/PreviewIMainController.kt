package cz.cuni.mff.ufal.translator.main.controller

import cz.cuni.mff.ufal.translator.ui.history.model.HistoryItem

/**
 * @author Tomas Krabac
 */
class PreviewIMainController : IMainController {
    override fun navigateHistory() {}
    override fun navigateMainScreen(item: HistoryItem) {}
    override fun navigateAboutScreen() {}
    override fun navigateSettingsScreen() {}
    override fun openWebUrl(url: String) {}
    override fun sendMail(mail: String) {}
    override fun onBackPressed() {}
}