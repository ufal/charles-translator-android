package cz.cuni.mff.ufal.translator.ui.history.screens.menu

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import cz.cuni.mff.ufal.translator.R


/**
 * @author Tomas Krabac
 */
sealed class HistoryNavItem(
    @StringRes val titleRes: Int,
    @DrawableRes val iconRes: Int,
    val screenRoute: String
) {

    object All : HistoryNavItem(
        titleRes = R.string.history_bottom_all,
        iconRes = R.drawable.ic_history,
        screenRoute = "all"
    )

    object Favourites : HistoryNavItem(
        titleRes = R.string.history_bottom_favourites,
        iconRes = R.drawable.ic_full_star,
        screenRoute = "favourites"
    )
}