package cz.cuni.mff.ufal.translator.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * @author Tomas Krabac
 */

val LightColorPalette = LindatColors(
    isLight = true,
    primary = Color(0xFFD22D40),
    primaryVariant = Color(0xFFD22D40),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF941C3F),
    secondaryVariant = Color(0xFF941C3F),
    onSecondary = Color(0xFFFFFFFF),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF121212),
    background = Color(0xFFFDE4E4),
    onBackground = Color(0xFF121212),
    error = Color(0xFFFF0000),
    onError = Color(0xFFE4F2FD),
    checkedThumbColor = Color(0xFFD22D40),
    uncheckedThumb = Color(0xFFECECEC),
    uncheckedTrack = Color(0xFFBDBDBD),
    toolbarBackground = Color(0xFFD22D40),
    statusBar = Color(0xFFD22D40),
    dialogBackgound = Color(0xFFFFFFFF),
    historyCardBackground = Color(0xFFFFFFFF),
    historyActionRow = Color(0xFFFDE4E4),
    selected = Color(0xFFD22D40),
    unselected = Color(0x77D22D40),
)

val DarkColorPalette = LindatColors(
    isLight = false,
    primary = Color(0xFFDF4444),
    primaryVariant = Color(0xFFDF4444),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFFAC0D3B),
    secondaryVariant = Color(0xFFAC0D3B),
    onSecondary = Color(0xFFFFFFFF),
    surface = Color(0xFF000000),
    onSurface = Color(0xFFFFFFFF),
    background = Color(0xFF121212),
    onBackground = Color(0xFFE4F2FD),
    error = Color(0xFFFF0000),
    onError = Color(0xFFE4F2FD),
    checkedThumbColor = Color(0xFFDF4444),
    uncheckedThumb = Color(0xFFECECEC),
    uncheckedTrack = Color(0xFFBDBDBD),
    toolbarBackground = Color(0xFF161616),
    statusBar = Color(0xFF161616),
    dialogBackgound = Color(0xFF424242),
    historyCardBackground = Color(0xFF424242),
    historyActionRow = Color(0xFF222222),
    selected = Color(0xFFDF4444),
    unselected = Color(0xFFFC9494),
)