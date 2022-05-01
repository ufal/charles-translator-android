package cz.cuni.mff.ufal.translator.main.controller

import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @author Tomas Krabac
 */
class PreviewIMainController : IMainController {

    override val isDarkMode = MutableStateFlow(false)

    override fun navigateHistory() {}
    override fun navigateAboutScreen() {}
    override fun navigateSettingsScreen() {}
    override fun openWebUrl(url: String) {}
    override fun sendMail(mail: String) {}
    override fun onBackPressed() {}
    override fun setDarkMode(isDarkMode: Boolean) {}
}