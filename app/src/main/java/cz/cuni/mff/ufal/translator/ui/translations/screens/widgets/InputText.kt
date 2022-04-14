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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.cuni.mff.ufal.translator.R
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
    var textFieldValue by remember { mutableStateOf(TextFieldValue()) }
    var lastSource by remember { mutableStateOf(TextSource.ClearButton) }
    val text = data.text

    val selection = if (lastSource != data.source) {
        lastSource = data.source
        TextRange(text.length)
    } else {
        textFieldValue.selection
    }
    textFieldValue = textFieldValue.copy(text, selection)


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
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 22.sp,
                ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.insert_text),
                        fontSize = 22.sp,
                    )
                },
                onValueChange = {
                    textFieldValue = it
                    onValueChange(InputTextData(it.text, TextSource.Keyboard))
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
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
        border = BorderStroke(1.dp, MaterialTheme.colors.primary),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.onBackground,
        )
    )
    {
        Icon(
            painter = painterResource(id = R.drawable.ic_paste),
            tint = MaterialTheme.colors.primary,
            contentDescription = stringResource(id = R.string.paste_cd),
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = stringResource(id = R.string.paste).uppercase(),
            fontSize = 16.sp,
            color = MaterialTheme.colors.primary
        )
    }
}

@Composable
private fun ClearItem(modifier: Modifier, onClick: () -> Unit) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_clear),
            tint = MaterialTheme.colors.primary,
            contentDescription = stringResource(id = R.string.clear_cd),
        )
    }
}
