package cz.cuni.mff.ufal.translator.ui.theme

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

/**
 * @author Tomas Krabac
 */

@Stable
class LindatColors(
    isLight: Boolean,
    primary: Color,
    primaryVariant: Color,
    onPrimary: Color,
    secondary: Color,
    secondaryVariant: Color,
    onSecondary: Color,
    surface: Color,
    onSurface: Color,
    background: Color,
    onBackground: Color,
    error: Color,
    onError: Color,
    uncheckedThumb: Color,
    uncheckedTrack: Color,
    toolbarBackground: Color,
    statusBar: Color,
    dialogBackgound: Color,
    historyCardBackground: Color,
    historyActionRow: Color,
    selected: Color,
    unselected: Color,
) {
    var isLight by mutableStateOf(isLight, structuralEqualityPolicy())
        internal set
    var primary by mutableStateOf(primary, structuralEqualityPolicy())
        internal set
    var primaryVariant by mutableStateOf(primaryVariant, structuralEqualityPolicy())
        internal set
    var onPrimary by mutableStateOf(onPrimary, structuralEqualityPolicy())
        internal set
    var secondary by mutableStateOf(secondary, structuralEqualityPolicy())
        internal set
    var secondaryVariant by mutableStateOf(secondaryVariant, structuralEqualityPolicy())
        internal set
    var onSecondary by mutableStateOf(onSecondary, structuralEqualityPolicy())
        internal set
    var surface by mutableStateOf(surface, structuralEqualityPolicy())
        internal set
    var onSurface by mutableStateOf(onSurface, structuralEqualityPolicy())
        internal set
    var background by mutableStateOf(background, structuralEqualityPolicy())
        internal set
    var onBackground by mutableStateOf(onBackground, structuralEqualityPolicy())
        internal set
    var error by mutableStateOf(error, structuralEqualityPolicy())
        internal set
    var onError by mutableStateOf(onError, structuralEqualityPolicy())
        internal set
    var uncheckedThumb by mutableStateOf(uncheckedThumb, structuralEqualityPolicy())
        internal set
    var uncheckedTrack by mutableStateOf(uncheckedTrack, structuralEqualityPolicy())
        internal set
    var toolbarBackground by mutableStateOf(toolbarBackground, structuralEqualityPolicy())
        internal set
    var statusBar by mutableStateOf(statusBar, structuralEqualityPolicy())
        internal set
    var dialogBackgound by mutableStateOf(dialogBackgound, structuralEqualityPolicy())
        internal set
    var historyCardBackground by mutableStateOf(historyCardBackground, structuralEqualityPolicy())
        internal set
    var historyActionRow by mutableStateOf(historyActionRow, structuralEqualityPolicy())
        internal set
    var selected by mutableStateOf(selected, structuralEqualityPolicy())
        internal set
    var unselected by mutableStateOf(unselected, structuralEqualityPolicy())
        internal set
}

internal val LocalLindatColors = staticCompositionLocalOf<LindatColors> {
    error("LindatColors not provided")
}