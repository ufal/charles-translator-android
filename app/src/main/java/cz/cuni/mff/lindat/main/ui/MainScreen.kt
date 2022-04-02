package cz.cuni.mff.lindat.main.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.launch
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.statusBarsPadding
import cz.cuni.mff.lindat.R
import cz.cuni.mff.lindat.base.BaseScreen
import cz.cuni.mff.lindat.main.controller.IController
import cz.cuni.mff.lindat.main.controller.PreviewIController
import cz.cuni.mff.lindat.main.viewmodel.IMainViewModel
import cz.cuni.mff.lindat.main.viewmodel.Language
import cz.cuni.mff.lindat.main.viewmodel.PreviewMainViewModel
import cz.cuni.mff.lindat.ui.common.FlagItem
import cz.cuni.mff.lindat.ui.theme.LindatTheme
import cz.cuni.mff.lindat.voice.VoiceContract

/**
 * @author Tomas Krabac
 */

@Composable
fun MainScreen(viewModel: IMainViewModel, controller: IController) {
    BaseScreen(viewModel = viewModel) {
        Content(
            viewModel = viewModel,
            controller = controller,
        )
    }
}

@Composable
fun Content(viewModel: IMainViewModel, controller: IController) {
    val inputText by viewModel.inputText.collectAsState()
    val outputTextCyrillic by viewModel.outputTextCyrillic.collectAsState()
    val outputTextLatin by viewModel.outputTextLatin.collectAsState()
    val inputLanguage by viewModel.inputLanguage.collectAsState()
    val outputLanguage by viewModel.outputLanguage.collectAsState()

    val isTextToSpeechAvailable = viewModel.isTextToSpeechAvailable(LocalContext.current)
    val mainText = if (outputLanguage == Language.Ukrainian) outputTextCyrillic else outputTextLatin
    val secondaryText = if (outputLanguage == Language.Ukrainian) outputTextLatin else outputTextCyrillic

    val voiceLauncher = rememberLauncherForActivityResult(VoiceContract()) { text ->
        if (text != null) {
            viewModel.setInputText(text)
        }
    }

    val context = LocalContext.current
    val clipboardLabel = stringResource(id = R.string.copy_to_clipboard_label)

    Column {
        Toolbar(
            isTextToSpeechAvailable = isTextToSpeechAvailable,
            startSpeechToText = { voiceLauncher.launch() },
            copyToClipBoard = {
                viewModel.copyToClipBoard(
                    context = context,
                    label = clipboardLabel,
                    text = mainText
                )
            },
            navigateHistory = { controller.navigateHistory() },
        )

        Spacer(modifier = Modifier.height(8.dp))

        SwapRow(inputLanguage = inputLanguage, outputLanguage = outputLanguage) {
            viewModel.swapLanguages()
        }

        InputText(
            modifier = Modifier.weight(0.5f),
            text = inputText,
            language = inputLanguage,
        ) {
            viewModel.setInputText(it)
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutputText(
            modifier = Modifier.weight(0.5f),
            mainText = mainText,
            secondaryText = secondaryText,
        )

        Spacer(modifier = Modifier.height(8.dp))

    }
}

@Composable
fun SwapRow(inputLanguage: Language, outputLanguage: Language, swapLanguages: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Label(modifier = Modifier.weight(3f), language = inputLanguage)

        SwapItem(modifier = Modifier.weight(1f), onClick = swapLanguages)

        Label(modifier = Modifier.weight(3f), language = outputLanguage)
    }
}

@Composable
fun Toolbar(
    isTextToSpeechAvailable: Boolean,
    copyToClipBoard: () -> Unit,
    startSpeechToText: () -> Unit,
    navigateHistory: () -> Unit,
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
                ActionItem(
                    drawableRes = R.drawable.ic_mic,
                    contentDescriptionRes = R.string.speech_to_text_cd
                ) {
                    startSpeechToText()
                }
            }

            ActionItem(
                drawableRes = R.drawable.ic_copy,
                contentDescriptionRes = R.string.copy_to_clipboard_cd
            ) {
                copyToClipBoard()
            }

            ActionItem(
                drawableRes = R.drawable.ic_history,
                contentDescriptionRes = R.string.history_cd
            ) {
                navigateHistory()
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
    mainText: String,
    secondaryText: String,
) {
    if (mainText.isEmpty()) {
        return
    }

    // SelectionContainer {
    Column(
        modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SelectionContainer {
            Text(
                text = mainText,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                ),
            )
        }

        SelectionContainer {
            Text(
                text = secondaryText,
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

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        FlagItem(language = language)

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
private fun ActionItem(
    @DrawableRes drawableRes: Int,
    @StringRes contentDescriptionRes: Int,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(id = drawableRes),
            tint = MaterialTheme.colors.onPrimary,
            contentDescription = stringResource(id = contentDescriptionRes),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    LindatTheme {
        MainScreen(
            viewModel = PreviewMainViewModel(),
            controller = PreviewIController(),
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun MainScreenDarkModePreview() {
    LindatTheme {
        MainScreen(
            viewModel = PreviewMainViewModel(),
            controller = PreviewIController(),
        )
    }
}