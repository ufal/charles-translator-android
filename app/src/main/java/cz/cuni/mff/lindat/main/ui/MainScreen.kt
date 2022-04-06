package cz.cuni.mff.lindat.main.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.launch
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.cuni.mff.lindat.R
import cz.cuni.mff.lindat.base.BaseScreen
import cz.cuni.mff.lindat.main.controller.IController
import cz.cuni.mff.lindat.main.controller.PreviewIController
import cz.cuni.mff.lindat.main.viewmodel.IMainViewModel
import cz.cuni.mff.lindat.main.viewmodel.Language
import cz.cuni.mff.lindat.main.viewmodel.MainScreenState
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
    val state by viewModel.state.collectAsState()
    val hasFinishedOnboarding by viewModel.hasFinishedOnboarding.collectAsState()

    // TODO: presunout do viewmodelu
    val mainText = if (outputLanguage == Language.Ukrainian) outputTextCyrillic else outputTextLatin
    val secondaryText = if (outputLanguage == Language.Ukrainian) outputTextLatin else outputTextCyrillic

    val context = LocalContext.current

    Column {

        if (!hasFinishedOnboarding) {
            FirstStartDialog { agreeWithDataCollection ->
                viewModel.setFinishedOnboarding(agreeWithDataCollection)
            }
        }

        SwapRow(inputLanguage = inputLanguage, outputLanguage = outputLanguage) {
            viewModel.swapLanguages()
        }

        InputText(
            modifier = Modifier.weight(3f),
            text = inputText,
            language = inputLanguage,
            hasFinishedOnboarding = hasFinishedOnboarding,

            pasteFromClipBoard = {
                viewModel.pasteFromClipBoard(context)
            },
            onValueChange = {
                viewModel.setInputText(it)
            }
        )

        when (state) {
            MainScreenState.Idle -> {
                // TODO: paste from clipboard
            }
            MainScreenState.Error -> {
                ErrorItem(modifier = Modifier) {
                    viewModel.retry()
                }
            }
            MainScreenState.Offline -> {
                OfflineItem(modifier = Modifier)
            }
            MainScreenState.Success, MainScreenState.Loading -> {
                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier
                        .weight(5f)
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .border(2.dp, MaterialTheme.colors.primary, RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colors.background)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    OutputText(
                        modifier = Modifier,
                        fontWeight = FontWeight.Bold,
                        text = mainText,
                    )

                    OutputText(
                        modifier = Modifier,
                        fontWeight = FontWeight.Normal,
                        text = secondaryText,
                    )
                }


            }
        }

        ActionsRow(
            viewModel = viewModel,
            controller = controller,
            mainText = mainText,
        )


    }
}

@Composable
private fun SwapRow(inputLanguage: Language, outputLanguage: Language, swapLanguages: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.weight(1F),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Label(modifier = Modifier.weight(1f), language = inputLanguage)

            SwapItem(modifier = Modifier, onClick = swapLanguages)

            Label(modifier = Modifier.weight(1f), language = outputLanguage)
        }

        ActionItem(drawableRes = R.drawable.ic_more, contentDescriptionRes = R.string.more_cd) {

        }
    }
}

@Composable
fun ActionsRow(
    viewModel: IMainViewModel,
    controller: IController,
    mainText: String,
) {
    val context = LocalContext.current
    val isSpeechRecognizerAvailable = viewModel.isSpeechRecognizerAvailable
    val isTextToSpeechAvailable by viewModel.isTextToSpeechAvailable.collectAsState()
    val clipboardLabel = stringResource(id = R.string.copy_to_clipboard_label)


    val voiceLauncher = rememberLauncherForActivityResult(VoiceContract()) { text ->
        if (text != null) {
            viewModel.setInputText(text)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Row(modifier = Modifier.align(Alignment.CenterStart)) {
            ActionItem(
                drawableRes = R.drawable.ic_history,
                contentDescriptionRes = R.string.history_cd
            ) {
                controller.navigateHistory()
            }
        }

        Row(modifier = Modifier.align(Alignment.Center)) {
            if (isSpeechRecognizerAvailable) {
                ActionItem(
                    modifier = Modifier.padding(vertical = 4.dp),
                    size = 44.dp,
                    drawableRes = R.drawable.ic_mic,
                    contentDescriptionRes = R.string.speech_recognizer_cd
                ) {
                    voiceLauncher.launch()
                }
            }
        }

        Row(modifier = Modifier.align(Alignment.CenterEnd)) {
            if (mainText.isNotBlank()) {
                ActionItem(
                    drawableRes = R.drawable.ic_copy,
                    contentDescriptionRes = R.string.copy_to_clipboard_cd
                ) {
                    viewModel.copyToClipBoard(
                        context = context,
                        label = clipboardLabel,
                        text = mainText
                    )
                }

                if (isTextToSpeechAvailable) {
                    ActionItem(
                        drawableRes = R.drawable.ic_tts,
                        contentDescriptionRes = R.string.tts_cd
                    ) {
                        viewModel.textToSpeech()
                    }
                }
            }
        }
    }
}

@Composable
fun InputText(
    modifier: Modifier,
    text: String,
    language: Language,
    hasFinishedOnboarding: Boolean,

    onValueChange: (text: String) -> Unit,
    pasteFromClipBoard: () -> Unit
) {

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(language, hasFinishedOnboarding) {
        if (hasFinishedOnboarding) {
            focusRequester.requestFocus()
        } else {
            focusRequester.freeFocus()
        }
    }

    var textFieldValue by remember(text) {
        mutableStateOf(
            TextFieldValue(text, TextRange(text.length))
        )
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier) {

        Box {
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    //  .border(2.dp, MaterialTheme.colors.primary, RoundedCornerShape(4.dp))
                    //.clip(RoundedCornerShape(8.dp))
                    // .background(MaterialTheme.colors.background)
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
                    onValueChange(it.text)
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
                    onValueChange("")
                }
            }
        }

    }
}

@Composable
private fun OutputText(
    modifier: Modifier,
    fontWeight: FontWeight,
    text: String,
) {
    SelectionContainer(modifier) {
        Text(
            text = text,
            style = TextStyle(
                fontWeight = fontWeight,
                fontSize = 22.sp,
            ),
        )
    }
}

@Composable
private fun ErrorItem(modifier: Modifier, retryAction: () -> Unit) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.api_error),
            color = MaterialTheme.colors.onBackground,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(14.dp))
        OutlinedButton(
            shape = RoundedCornerShape(50),
            onClick = retryAction,
            border = BorderStroke(1.dp, color = MaterialTheme.colors.onBackground),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.onBackground,
            )
        ) {
            Text(
                text = stringResource(id = R.string.try_again).uppercase(),
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
            )
        }
    }
}

@Composable
private fun OfflineItem(modifier: Modifier) {
// TODO: 04.04.2022 tomaskrabac:  implement
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
            tint = MaterialTheme.colors.primary,
            contentDescription = stringResource(id = R.string.clear_cd),
        )
    }
}

@Composable
private fun ActionItem(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    @DrawableRes drawableRes: Int,
    @StringRes contentDescriptionRes: Int,
    onClick: () -> Unit,
) {

    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier.size(size),
            painter = painterResource(id = drawableRes),
            tint = MaterialTheme.colors.primary,
            contentDescription = stringResource(id = contentDescriptionRes),
        )
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