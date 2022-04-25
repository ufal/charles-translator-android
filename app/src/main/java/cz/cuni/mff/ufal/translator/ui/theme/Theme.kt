package cz.cuni.mff.ufal.translator.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

private object MainRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor(): Color = MaterialTheme.colors.primary

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleTheme.defaultRippleAlpha(
        Color.Black,
        lightTheme = !isSystemInDarkTheme()
    )
}

@Composable
fun LindatTheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors.toMaterialColors(),
        typography = Typography,
        shapes = Shapes,
    ) {
        CompositionLocalProvider(
            LocalLindatColors provides colors,
            LocalRippleTheme provides MainRippleTheme,
            content = content
        )
    }
}

@Composable
fun LindatThemePreview(content: @Composable () -> Unit) {
    LindatTheme(isSystemInDarkTheme(), content)
}

object LindatTheme {
    val colors: LindatColors
        @Composable
        @ReadOnlyComposable
        get() = LocalLindatColors.current

}

private fun LindatColors.toMaterialColors(): Colors {
    return Colors(
        isLight = isLight,
        primary = primary,
        primaryVariant = primaryVariant,
        secondary = secondary,
        secondaryVariant = secondaryVariant,
        onSecondary = onSecondary,
        onPrimary = onPrimary,
        surface = surface,
        onSurface = onSurface,
        background = background,
        onBackground = onBackground,
        error = error,
        onError = onError,
    )
}