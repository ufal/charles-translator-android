package cz.cuni.mff.ufal.translator.ui.settings.screens.widgets

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.ui.theme.LindatColors
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme
import cz.cuni.mff.ufal.translator.ui.theme.LindatThemePreview

/**
 * @author Tomas Krabac
 */

@Composable
fun SettingSwitchItem(
    @StringRes titleRes: Int,
    @StringRes descriptionRes: Int,
    isChecked: Boolean,

    onCheckedChange: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onCheckedChange(!isChecked)
            },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(16.dp))

            Text(
                modifier = Modifier.weight(1F),
                text = stringResource(id = titleRes),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = LindatTheme.colors.onSurface,
            )

            Spacer(modifier = Modifier.width(4.dp))

            Switch(
                colors = SwitchDefaults.colors(
                    checkedThumbColor = LindatTheme.colors.checkedThumbColor,
                    uncheckedThumbColor = LindatTheme.colors.uncheckedThumb,
                    uncheckedTrackColor = LindatTheme.colors.uncheckedTrack,
                    uncheckedTrackAlpha = 1f
                ),
                checked = isChecked,
                onCheckedChange = {
                    onCheckedChange(it)
                },
            )

            Spacer(modifier = Modifier.width(16.dp))
        }

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = descriptionRes),
            fontSize = 14.sp,
            color = LindatTheme.colors.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsItemCheckedPreview() {
    LindatThemePreview {
        SettingSwitchItem(
            titleRes = R.string.settings_data_collection_title,
            descriptionRes = R.string.settings_data_collection_description,
            isChecked = true,

            onCheckedChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsItemUncheckedPreview() {
    LindatThemePreview {
        SettingSwitchItem(
            titleRes = R.string.settings_data_collection_title,
            descriptionRes = R.string.settings_data_collection_description,
            isChecked = false,

            onCheckedChange = {}
        )
    }
}
