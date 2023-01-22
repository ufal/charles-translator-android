package cz.cuni.mff.ufal.translator.interactors.crashlytics

/**
 * @author Tomas Krabac
 */
enum class Screen(val key: String) {
    Translations("translations"),
    Settings("settings"),
    About("about"),
    HistoryAll("history_all"),
    HistoryFavourites("history_favourites"),
    Conversation("conversation"),
}