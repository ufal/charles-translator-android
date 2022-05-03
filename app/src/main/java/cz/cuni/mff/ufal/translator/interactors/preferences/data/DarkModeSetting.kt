package cz.cuni.mff.ufal.translator.interactors.preferences.data

import androidx.annotation.StringRes
import cz.cuni.mff.ufal.translator.R

/**
 * @author Tomas Krabac
 */
enum class DarkModeSetting(
    val key: String,
    @StringRes val labelRes: Int,
) {
    System("system", R.string.settings_dark_mode_value_system),
    Enabled("enabled", R.string.settings_dark_mode_value_enabled),
    Disabled("disabled", R.string.settings_dark_mode_value_disabled),
}