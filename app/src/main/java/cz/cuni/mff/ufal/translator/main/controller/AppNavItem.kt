package cz.cuni.mff.ufal.translator.main.controller


sealed class AppNavItem(val screenRoute: String) {
    data object Home : AppNavItem("home")
    data object History : AppNavItem("history")
    data object About : AppNavItem("about")
    data object Settings : AppNavItem("settings")
    data object Conversation : AppNavItem("conversation")
}

