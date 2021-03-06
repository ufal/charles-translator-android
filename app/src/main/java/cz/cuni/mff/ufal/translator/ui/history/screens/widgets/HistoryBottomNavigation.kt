package cz.cuni.mff.ufal.translator.ui.history.screens.widgets

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import cz.cuni.mff.ufal.translator.ui.history.screens.menu.HistoryNavItem
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme

/**
 * @author Tomas Krabac
 */
@Composable
fun HistoryBottomNavigation(navController: NavController) {
    val items = listOf(
        HistoryNavItem.All,
        HistoryNavItem.Favourites,
    )
    BottomNavigation(
        backgroundColor = LindatTheme.colors.surface,
        contentColor = LindatTheme.colors.onSurface,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            val title = stringResource(id = item.titleRes)

            // TODO: 08.04.2022 tomaskrabac:  use currentRoute == item.screenRoute for different icons, text style
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = title
                    )
                },
                label = {
                    Text(
                        text = title,
                    )
                },
                selectedContentColor = LindatTheme.colors.selected,
                unselectedContentColor = LindatTheme.colors.unselected,
                alwaysShowLabel = true,
                selected = currentRoute == item.screenRoute,
                onClick = {
                    navController.navigate(item.screenRoute) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}