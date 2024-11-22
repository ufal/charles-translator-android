package cz.cuni.mff.ufal.translator.ui.translations.screens.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.interactors.DiacriticsFixer
import cz.cuni.mff.ufal.translator.ui.common.widgets.ClearItem
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme
import cz.cuni.mff.ufal.translator.ui.translations.models.InputTextData
import cz.cuni.mff.ufal.translator.ui.translations.models.Language
import cz.cuni.mff.ufal.translator.ui.translations.models.TextSource
import kotlinx.coroutines.delay

/**
 * @author Tomas Krabac
 */

@Composable
fun InputText(
    modifier: Modifier,
    data: InputTextData,
    language: Language,
    hasFinishedOnboarding: Boolean,

    onValueChange: (data: InputTextData) -> Unit,
    pasteFromClipBoard: () -> Unit
) {
    val text = data.text
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = text)) }
    val textFieldValue = textFieldValueState.copy(text = text)

    if (data.source != TextSource.Keyboard && textFieldValueState.text != text) {
        textFieldValueState = textFieldValue.copy(text, TextRange(text.length))
    }

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(language, hasFinishedOnboarding) {
        delay(200)  //tiny hack for keyboard shown after dialog

        if (hasFinishedOnboarding) {
            focusRequester.requestFocus()
        } else {
            focusRequester.freeFocus()
        }
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier) {

        Box {
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxSize()
                    .focusRequester(focusRequester),
                value = textFieldValue,
                textStyle = TextStyle(
                    color = LindatTheme.colors.onBackground,
                    fontSize = 22.sp,
                ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.insert_text),
                        fontSize = 22.sp,
                    )
                },
                trailingIcon = {
                    Box(Modifier.size(16.dp)) //fake end padding
                },
                onValueChange = {
                    val fixedText = DiacriticsFixer.fixDiacritic(it.text)
                    textFieldValueState = it.copy(fixedText)

                    if (text != it.text) {
                        onValueChange(InputTextData(fixedText, TextSource.Keyboard))
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Go,
                    hintLocales = LocaleList(language.bcp47Code)
                ),
                keyboardActions = KeyboardActions(
                    onGo = { keyboardController?.hide() })
            )

            if (text.isEmpty()) {
                ActionPasteItem(
                    modifier = Modifier.padding(start = 20.dp, top = 52.dp),
                    onClick = pasteFromClipBoard
                )
            }

            if (text.isNotEmpty()) {
                ClearItem(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 8.dp)
                ) {
                    textFieldValueState = TextFieldValue()
                    onValueChange(InputTextData("", TextSource.ClearButton))
                }
            }
        }

    }
}

@Composable
private fun ActionPasteItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    OutlinedButton(
        modifier = modifier,
        shape = RoundedCornerShape(80),
        onClick = onClick,
        border = BorderStroke(1.dp, LindatTheme.colors.primary),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = LindatTheme.colors.background,
            contentColor = LindatTheme.colors.onBackground,
        )
    )
    {
        Icon(
            painter = painterResource(id = R.drawable.ic_paste),
            tint = LindatTheme.colors.primary,
            contentDescription = stringResource(id = R.string.paste_cd),
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = stringResource(id = R.string.paste).uppercase(),
            fontSize = 16.sp,
            color = LindatTheme.colors.primary
        )
    }
}
