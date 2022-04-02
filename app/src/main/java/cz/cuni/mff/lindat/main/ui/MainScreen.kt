package cz.cuni.mff.lindat.main.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import cz.cuni.mff.lindat.main.viewActions.IMainViewActions
import cz.cuni.mff.lindat.main.viewActions.PreviewMainViewActions
import cz.cuni.mff.lindat.main.viewmodel.IMainViewModel
import cz.cuni.mff.lindat.main.viewmodel.Language
import cz.cuni.mff.lindat.main.viewmodel.PreviewMainViewModel
import cz.cuni.mff.lindat.ui.theme.LindatTheme
import cz.cuni.mff.lindat.R

/**
 * @author Tomas Krabac
 */

@Composable
fun MainScreen(viewModel: IMainViewModel, viewActions: IMainViewActions) {
    LindatTheme {
        ProvideWindowInsets {
            Surface(modifier = Modifier.fillMaxSize()) {
                Content(
                    viewModel = viewModel,
                    viewActions = viewActions,
                )
            }
        }
    }
}

@Composable
fun Content(viewModel: IMainViewModel, viewActions: IMainViewActions) {
    val inputText by viewModel.inputText.collectAsState()
    val outputTextCyrillic by viewModel.outputTextCyrillic.collectAsState()
    val outputTextLatin by viewModel.outputTextLatin.collectAsState()
    val inputLanguage by viewModel.inputLanguage.collectAsState()
    val outputLanguage by viewModel.outputLanguage.collectAsState()
    val showCyrillic by viewModel.showCyrillic.collectAsState()

    val isTextToSpeechAvailable = viewModel.isTextToSpeechAvailable(LocalContext.current)
    val outputText = if (showCyrillic) outputTextCyrillic else outputTextLatin
    val copyToClipBoardLabel = stringResource(id = R.string.copy_to_clipboard_label)

    Column {
        Toolbar(
            isTextToSpeechAvailable = isTextToSpeechAvailable,
            startSpeechToText = { viewActions.startSpeechToText() },
            copyToClipBoard = { viewActions.copyToClipBoard(copyToClipBoardLabel, outputText) },
        )

        Spacer(modifier = Modifier.height(8.dp))

        InputText(
            modifier = Modifier.weight(0.5f),
            text = inputText,
            language = inputLanguage,
        ) {
            viewModel.setInputText(it)
        }

        SwapItem(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            viewModel.swapLanguages()
        }

        OutputText(
            modifier = Modifier.weight(0.5f),
            text = outputText,
            language = outputLanguage,
            showCyrillic = showCyrillic,
            setShowCyrillic = viewModel::setShowCyrillic
        )

        Spacer(modifier = Modifier.height(8.dp))

    }
}

@Composable
fun Toolbar(
    isTextToSpeechAvailable: Boolean,
    copyToClipBoard: () -> Unit,
    startSpeechToText: () -> Unit,
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        title = {
            Text(
                text = stringResource(id = R.string.title),
                color = MaterialTheme.colors.onPrimary,
            )
        },
        modifier = Modifier.statusBarsPadding(),
        elevation = 4.dp,
        actions = {
            if (isTextToSpeechAvailable) {
                SpeechToTextItem {
                    startSpeechToText()
                }
            }

            CopyToClipboardItem {
                copyToClipBoard()
            }
        },
    )
}

@Composable
fun InputText(
    modifier: Modifier,
    text: String,
    language: Language,
    onValueChange: (text: String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(language) {
        focusRequester.requestFocus()
    }

    var textFieldValue by remember(text) {
        mutableStateOf(
            TextFieldValue(text, TextRange(text.length))
        )
    }

    Column(modifier.padding(horizontal = 16.dp)) {
        Label(Modifier.padding(bottom = 4.dp), language)

        Box {
            BasicTextField(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colors.background)
                    .padding(start = 8.dp, end = 40.dp, top = 8.dp, bottom = 8.dp)
                    .fillMaxSize()
                    .focusRequester(focusRequester),
                value = textFieldValue,
                textStyle = TextStyle(
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 18.sp,
                ),
                cursorBrush = SolidColor(MaterialTheme.colors.primary),
                onValueChange = {
                    textFieldValue = it
                    onValueChange(it.text)
                },
            )

            if (text.isNotEmpty()) {
                ClearItem(modifier = Modifier.align(Alignment.TopEnd)) {
                    onValueChange("")
                }
            }
        }

    }
}

@Composable
private fun OutputText(
    modifier: Modifier,
    text: String,
    language: Language,
    showCyrillic: Boolean,
    setShowCyrillic: (value: Boolean) -> Unit
) {
    Column(modifier.padding(horizontal = 16.dp)) {
        Row(modifier = Modifier.padding(bottom = 4.dp)) {
            Label(language = language)

            Spacer(modifier = Modifier.weight(1f))

            ShowCyrilicSwitchItem(showCyrillic) {
                setShowCyrillic(it)
            }
        }


        SelectionContainer {
            Text(
                modifier = modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colors.background)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                text = text,
                style = TextStyle(
                    fontSize = 18.sp,
                ),
            )
        }
    }
}

@Composable
private fun Label(modifier: Modifier = Modifier, language: Language) {
    val labelRes = when (language) {
        Language.Czech -> R.string.cz_label
        Language.Ukrainian -> R.string.uk_label
    }

    val iconRes = when (language) {
        Language.Czech -> R.drawable.ic_czech_flag
        Language.Ukrainian -> R.drawable.ic_ukraine_flag
    }

    val iconContentDescriptionRes = when (language) {
        Language.Czech -> R.string.czech_flag_cd
        Language.Ukrainian -> R.string.ukraine_flag_cd
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.height(16.dp),
            painter = painterResource(id = iconRes),
            contentDescription = stringResource(id = iconContentDescriptionRes),
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = stringResource(id = labelRes),
            color = MaterialTheme.colors.onSurface
        )
    }

}

@Composable
private fun ShowCyrilicSwitchItem(showCyrillic: Boolean, setShowCyrillic: (value: Boolean) -> Unit) {
    val checkedState = remember { mutableStateOf(showCyrillic) }

    Text(
        text = stringResource(id = R.string.cyrillic_label),
        color = MaterialTheme.colors.onSurface
    )

    Spacer(modifier = Modifier.width(4.dp))

    Switch(
        checked = checkedState.value,
        onCheckedChange = {
            checkedState.value = it
            setShowCyrillic(it)
        },
        colors = SwitchDefaults.colors(
            checkedThumbColor = MaterialTheme.colors.primary,
            uncheckedThumbColor = MaterialTheme.colors.secondary,
            uncheckedTrackColor = MaterialTheme.colors.primary,
        )
    )
}

@Composable
private fun SwapItem(modifier: Modifier, onClick: () -> Unit) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier.rotate(90f),
            painter = painterResource(id = R.drawable.ic_swap),
            tint = MaterialTheme.colors.primary,
            contentDescription = stringResource(id = R.string.swap_cd),
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
            tint = MaterialTheme.colors.onBackground,
            contentDescription = stringResource(id = R.string.clear_cd),
        )
    }
}

@Composable
private fun SpeechToTextItem(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_mic),
            tint = MaterialTheme.colors.onPrimary,
            contentDescription = stringResource(id = R.string.speech_to_text_cd),
        )
    }
}

@Composable
private fun CopyToClipboardItem(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_copy),
            tint = MaterialTheme.colors.onPrimary,
            contentDescription = stringResource(id = R.string.copy_to_clipboard_cd),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    LindatTheme {
        MainScreen(
            viewModel = PreviewMainViewModel(),
            viewActions = PreviewMainViewActions(),
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun MainScreenDarkModePreview() {
    LindatTheme {
        MainScreen(
            viewModel = PreviewMainViewModel(),
            viewActions = PreviewMainViewActions(),
        )
    }
}