package cz.cuni.mff.ufal.translator.main.controller

import cz.cuni.mff.ufal.translator.interactors.preferences.data.DarkModeSetting
import cz.cuni.mff.ufal.translator.ui.history.model.HistoryItem
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @author Tomas Krabac
 */
class PreviewIMainController : IMainController {

    override val darkModeSetting = MutableStateFlow(DarkModeSetting.System)

    override fun navigateHistory() {}
    override fun navigateAboutScreen() {}
    override fun navigateSettingsScreen() {}
    override fun navigateConversationScreen() {}
    override fun openWebUrl(url: String) {}
    override fun sendMail(mail: String) {}
    override fun onBackPressed() {}
    override fun setDarkModeSettings(darkModeSetting: DarkModeSetting) {}
    override fun setHistoryItem(item: HistoryItem) {}
}