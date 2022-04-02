package cz.cuni.mff.lindat.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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


/* // TODO: 02.04.2022 tomaskrabac: dodelat
private val DarkColorPalette = darkColors(
    primary = Color(0xFF202020),
    onPrimary = Color(0xFFFFFFFF),
    surface = Color(0xFF121212),
    onSurface =  Color(0xFFFFFFFF),
    background = Color(0xFF303030),
    onBackground =  Color(0xFFFFFFFF),

)
 */

private val DarkColorPalette = lightColors(
    primary = Color(0xFF2196F3),
    onPrimary = Color(0xFFFFFFFF),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF121212),
    background = Color(0xFFE4F2FD),
    onBackground = Color(0xFF121212),
)

private val LightColorPalette = lightColors(
    primary = Color(0xFF2196F3),
    onPrimary = Color(0xFFFFFFFF),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF121212),
    background = Color(0xFFE4F2FD),
    onBackground = Color(0xFF121212),
)

@Composable
fun LindatTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
    ) {
        CompositionLocalProvider(
            LocalRippleTheme provides MainRippleTheme,
            content = content
        )
    }
}