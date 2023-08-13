package cz.cuni.mff.ufal.translator.ui.conversation.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.base.BaseScreen
import cz.cuni.mff.ufal.translator.interactors.crashlytics.Screen
import cz.cuni.mff.ufal.translator.main.controller.IMainController
import cz.cuni.mff.ufal.translator.main.controller.PreviewIMainController
import cz.cuni.mff.ufal.translator.ui.common.widgets.BaseToolbar
import cz.cuni.mff.ufal.translator.ui.conversation.models.BubblePosition
import cz.cuni.mff.ufal.translator.ui.conversation.models.ConversationModel
import cz.cuni.mff.ufal.translator.ui.conversation.screens.widgets.ActionsRow
import cz.cuni.mff.ufal.translator.ui.conversation.screens.widgets.LeftBubble
import cz.cuni.mff.ufal.translator.ui.conversation.screens.widgets.RightBubble
import cz.cuni.mff.ufal.translator.ui.conversation.viewmodel.ConversationViewModel
import cz.cuni.mff.ufal.translator.ui.conversation.viewmodel.IConversationViewModel
import cz.cuni.mff.ufal.translator.ui.conversation.viewmodel.PreviewConversationViewModel
import cz.cuni.mff.ufal.translator.ui.theme.LindatThemePreview

/**
 * @author Tomas Krabac
 */
@Destination
@Composable
fun ConversationScreen(
    viewModel: IConversationViewModel = hiltViewModel<ConversationViewModel>(),
    mainController: IMainController
) {
    BaseScreen(
        screen = Screen.Conversation,
        darkModeSetting = mainController.darkModeSetting,
        viewModel = viewModel,
    ) {
        Content(
            viewModel = viewModel,
            mainController = mainController,
        )
    }
}

@Composable
private fun Content(viewModel: IConversationViewModel, mainController: IMainController) {
    val isSpeechRecognizerAvailable = viewModel.isSpeechRecognizerAvailable
    val isListening by viewModel.isListening.collectAsState()
    val isOffline by viewModel.isOffline.collectAsState()
    val rmsdB by viewModel.rmsdB.collectAsState()
    val activeLanguage by viewModel.activeLanguage.collectAsState()
    val leftLanguage = viewModel.leftLanguage
    val rightLanguage by viewModel.rightLanguage.collectAsState()
    val rightLanguages = viewModel.rightLanguages
    val conversation by viewModel.conversation.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(conversation) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }

    Column {
        BaseToolbar(titleRes = R.string.conversation_title) {
            mainController.onBackPressed()
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .weight(weight = 1f, fill = true)
        ) {
            when {
                isOffline -> {
                    InfoText(textRes = R.string.offline_text)
                }

                conversation.isEmpty() -> {
                    InfoText(textRes = R.string.conversation_empty)
                }

                else -> {
                    Conversations(conversation)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        ActionsRow(
            isSpeechRecognizerAvailable = isSpeechRecognizerAvailable,
            isListening = isListening,
            rmsdB = rmsdB,
            activeLanguage = activeLanguage,
            leftLanguage = leftLanguage,
            rightLanguage = rightLanguage,
            rightLanguages = rightLanguages,

            startRecognizeAudio = viewModel::startRecognizeAudio,
            stopRecognizeAudio = viewModel::stopRecognizeAudio,
            onLanguageSelect = viewModel::setRightLanguage,
        )
    }
}

@Composable
private fun Conversations(conversationData: List<ConversationModel>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        conversationData.forEach { model ->
            val outputTextData by model.text.collectAsState()

            when (model.position) {
                BubblePosition.Left ->
                    LeftBubble(
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(end = 36.dp),
                        data = outputTextData
                    )

                BubblePosition.Right ->
                    RightBubble(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(start = 36.dp),
                        data = outputTextData
                    )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun InfoText(
    @StringRes textRes: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(id = textRes),
            fontWeight = FontWeight.W600,
            fontSize = 24.sp,
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    LindatThemePreview {
        ConversationScreen(
            viewModel = PreviewConversationViewModel(),
            mainController = PreviewIMainController(),
        )
    }
}



