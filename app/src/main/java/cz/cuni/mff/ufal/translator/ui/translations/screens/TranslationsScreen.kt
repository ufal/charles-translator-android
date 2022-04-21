package cz.cuni.mff.ufal.translator.ui.translations.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import cz.cuni.mff.ufal.translator.base.BaseScreen
import cz.cuni.mff.ufal.translator.main.controller.IMainController
import cz.cuni.mff.ufal.translator.main.controller.PreviewIMainController
import cz.cuni.mff.ufal.translator.ui.destinations.MainHistoryScreenDestination
import cz.cuni.mff.ufal.translator.ui.history.model.HistoryItem
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme
import cz.cuni.mff.ufal.translator.ui.translations.models.TranslationsScreenState
import cz.cuni.mff.ufal.translator.ui.translations.screens.widgets.*
import cz.cuni.mff.ufal.translator.ui.translations.viewmodel.ITranslationsViewModel
import cz.cuni.mff.ufal.translator.ui.translations.viewmodel.PreviewTranslationsViewModel
import cz.cuni.mff.ufal.translator.ui.translations.viewmodel.TranslationsViewModel

/**
 * @author Tomas Krabac
 */

@Destination(start = true)
@Composable
fun TranslationsScreen(
    viewModel: ITranslationsViewModel = hiltViewModel<TranslationsViewModel>(),
    mainController: IMainController,
    resultRecipient: ResultRecipient<MainHistoryScreenDestination, HistoryItem>
) {
    resultRecipient.onNavResult { result ->
        if (result is NavResult.Value) {
            viewModel.setFromHistoryItem(result.value)
        }
    }

    BaseScreen(viewModel = viewModel) {
        Content(
            viewModel = viewModel,
            mainController = mainController,
        )
    }
}

@Composable
fun Content(
    viewModel: ITranslationsViewModel,
    mainController: IMainController,
) {
    val inputTextData by viewModel.inputTextData.collectAsState()
    val outputTextData by viewModel.outputTextData.collectAsState()
    val inputLanguage by viewModel.inputLanguage.collectAsState()
    val outputLanguage by viewModel.outputLanguage.collectAsState()
    val state by viewModel.state.collectAsState()
    val hasFinishedOnboarding by viewModel.hasFinishedOnboarding.collectAsState()

    Column {

        if (!hasFinishedOnboarding) {
            FirstStartDialog { agreeWithDataCollection ->
                viewModel.setFinishedOnboarding(agreeWithDataCollection)
            }
        }

        SwapRow(
            inputLanguage = inputLanguage,
            outputLanguage = outputLanguage,

            swapLanguages = viewModel::swapLanguages,
            onAboutAppClicked = mainController::navigateAboutScreen,
            onSettingsClicked = mainController::navigateSettingsScreen,
        )

        InputText(
            modifier = Modifier.weight(3f),
            data = inputTextData,
            language = inputLanguage,
            hasFinishedOnboarding = hasFinishedOnboarding,

            pasteFromClipBoard = {
                viewModel.pasteFromClipBoard()
            },
            onValueChange = {
                viewModel.setInputText(it)
            }
        )

        when (state) {
            is TranslationsScreenState.Idle -> {
                // nothing
            }
            is TranslationsScreenState.UnSupportedApiError -> {
                UnsupportedApiDialog(LocalContext.current, (state as TranslationsScreenState.UnSupportedApiError).data)
            }
            is TranslationsScreenState.Error -> {
                ErrorItem(modifier = Modifier) {
                    viewModel.retry()
                }
            }
            is TranslationsScreenState.Offline -> {
                OfflineItem(modifier = Modifier)
            }
            is TranslationsScreenState.Success, is TranslationsScreenState.Loading -> {
                Spacer(modifier = Modifier.height(8.dp))

                OutputItem(
                    modifier = Modifier.weight(5f),
                    data = outputTextData
                )
            }
        }

        ActionsRow(
            viewModel = viewModel,
            mainController = mainController,
            mainText = outputTextData.mainText,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    LindatTheme {
        Content(
            viewModel = PreviewTranslationsViewModel(),
            mainController = PreviewIMainController(),
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun MainScreenDarkModePreview() {
    LindatTheme {
        Content(
            viewModel = PreviewTranslationsViewModel(),
            mainController = PreviewIMainController(),
        )
    }
}