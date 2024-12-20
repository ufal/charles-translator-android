package cz.cuni.mff.ufal.translator.ui.translations.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cz.cuni.mff.ufal.translator.base.BaseScreen
import cz.cuni.mff.ufal.translator.interactors.crashlytics.Screen
import cz.cuni.mff.ufal.translator.main.controller.IMainController
import cz.cuni.mff.ufal.translator.main.controller.PreviewIMainController
import cz.cuni.mff.ufal.translator.ui.history.model.HistoryItem
import cz.cuni.mff.ufal.translator.ui.theme.LindatThemePreview
import cz.cuni.mff.ufal.translator.ui.translations.models.TranslationsScreenState
import cz.cuni.mff.ufal.translator.ui.translations.screens.widgets.ActionsRow
import cz.cuni.mff.ufal.translator.ui.translations.screens.widgets.ErrorItem
import cz.cuni.mff.ufal.translator.ui.translations.screens.widgets.InputText
import cz.cuni.mff.ufal.translator.ui.translations.screens.widgets.MaxCharactersErrorItem
import cz.cuni.mff.ufal.translator.ui.translations.screens.widgets.OfflineItem
import cz.cuni.mff.ufal.translator.ui.translations.screens.widgets.OutputItem
import cz.cuni.mff.ufal.translator.ui.translations.screens.widgets.SwapRow
import cz.cuni.mff.ufal.translator.ui.translations.viewmodel.ITranslationsViewModel
import cz.cuni.mff.ufal.translator.ui.translations.viewmodel.PreviewTranslationsViewModel
import cz.cuni.mff.ufal.translator.ui.translations.viewmodel.TranslationsViewModel

/**
 * @author Tomas Krabac
 */

@Composable
fun TranslationsScreen(
    viewModel: ITranslationsViewModel = hiltViewModel<TranslationsViewModel>(),
    mainController: IMainController,
    historyItem: HistoryItem?
) {
    if (historyItem != null) {
        viewModel.setFromHistoryItem(historyItem)
    }

    BaseScreen(
        screen = Screen.Translations,
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
fun Content(
    viewModel: ITranslationsViewModel,
    mainController: IMainController,
) {
    val inputTextData by viewModel.inputTextData.collectAsState()
    val outputTextData by viewModel.outputTextData.collectAsState()
    val inputLanguage by viewModel.inputLanguage.collectAsState()
    val inputLanguages by viewModel.inputLanguages.collectAsState()
    val outputLanguage by viewModel.outputLanguage.collectAsState()
    val outputLanguages by viewModel.outputLanguages.collectAsState()
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
            inputLanguages = inputLanguages,
            outputLanguage = outputLanguage,
            outputLanguages = outputLanguages,

            swapLanguages = viewModel::swapLanguages,
            onInputLanguageSelect = viewModel::setInputLanguage,
            onOutputLanguageSelect = viewModel::setOutputLanguage,
            onAboutAppClicked = mainController::navigateAboutScreen,
            onSettingsClicked = mainController::navigateSettingsScreen,
        )

        InputText(
            modifier = Modifier
                .weight(3f)
                .onFocusChanged {
                    if (!it.isFocused) {
                        viewModel.stopRecognizeAudio()
                    }
                },
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

            TranslationsScreenState.MaxCharactersLimitError -> {
                MaxCharactersErrorItem(modifier = Modifier)
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
    LindatThemePreview {
        Content(
            viewModel = PreviewTranslationsViewModel(),
            mainController = PreviewIMainController(),
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun MainScreenDarkModePreview() {
    LindatThemePreview {
        Content(
            viewModel = PreviewTranslationsViewModel(),
            mainController = PreviewIMainController(),
        )
    }
}