package cz.cuni.mff.ufal.translator.ui.settings.screens.widgets

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.imePadding
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.ui.common.widgets.ClearItem
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme

/**
 * @author Tomas Krabac
 */

@Composable
fun SettingEditTextItem(
    @StringRes labelRes: Int,
    @StringRes placeholderRes: Int,
    value: String,

    onValueChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .imePadding()
            .fillMaxWidth(),
    ) {
        var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }
        val textFieldValue = textFieldValueState.copy(text = value)

        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            value = textFieldValue,
            textStyle = TextStyle(
                color = MaterialTheme.colors.onBackground,
                fontSize = 22.sp,
            ),
            singleLine = true,
            label = {
                Text(
                    text = stringResource(id = labelRes),
                    fontSize = 16.sp,
                )
            },
            placeholder = {
                Text(
                    text = stringResource(id = placeholderRes),
                    fontSize = 14.sp,
                )
            },
            trailingIcon = {
                if (textFieldValue.text.isNotEmpty()) {
                    ClearItem(onClick = {
                        textFieldValueState = TextFieldValue()
                        onValueChange("")
                    })
                }
            },
            onValueChange = {
                textFieldValueState = it
                if (value != it.text) {
                    onValueChange(it.text)
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsSingleItemPreview() {
    LindatTheme {
        SettingEditTextItem(
            labelRes = R.string.settings_organization_name_label,
            placeholderRes = R.string.settings_organization_name_placeholder,
            value = "Organization",

            onValueChange = {}
        )
    }
}
