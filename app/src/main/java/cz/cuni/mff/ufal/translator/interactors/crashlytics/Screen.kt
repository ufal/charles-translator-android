package cz.cuni.mff.ufal.translator.interactors.crashlytics

/**
 * @author Tomas Krabac
 */
enum class Screen(val value: String) {
    Translations("translations"),
    Settings("settings"),
    About("about"),
    HistoryAll("history_all"),
    HistoryFavourites("history_favourites"),
}