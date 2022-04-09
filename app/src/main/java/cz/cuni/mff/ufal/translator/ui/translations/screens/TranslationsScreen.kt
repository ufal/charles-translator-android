package cz.cuni.mff.ufal.translator.ui.translations.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.cuni.mff.ufal.translator.base.BaseScreen
import cz.cuni.mff.ufal.translator.main.controller.IMainController
import cz.cuni.mff.ufal.translator.main.controller.PreviewIMainController
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme
import cz.cuni.mff.ufal.translator.ui.translations.models.TranslationsScreenState
import cz.cuni.mff.ufal.translator.ui.translations.screens.widgets.*
import cz.cuni.mff.ufal.translator.ui.translations.viewmodel.ITranslationsViewModel
import cz.cuni.mff.ufal.translator.ui.translations.viewmodel.PreviewTranslationsViewModel

/**
 * @author Tomas Krabac
 */

@Composable
fun TranslationsScreen(viewModel: ITranslationsViewModel, mainController: IMainController) {
    BaseScreen(viewModel = viewModel) {
        Content(
            viewModel = viewModel,
            mainController = mainController,
        )
    }
}

@Composable
fun Content(viewModel: ITranslationsViewModel, mainController: IMainController) {
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
            TranslationsScreenState.Idle -> {
                // nothing
            }
            TranslationsScreenState.Error -> {
                ErrorItem(modifier = Modifier) {
                    viewModel.retry()
                }
            }
            TranslationsScreenState.Offline -> {
                OfflineItem(modifier = Modifier)
            }
            TranslationsScreenState.Success, TranslationsScreenState.Loading -> {
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
        TranslationsScreen(
            viewModel = PreviewTranslationsViewModel(),
            mainController = PreviewIMainController(),
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun MainScreenDarkModePreview() {
    LindatTheme {
        TranslationsScreen(
            viewModel = PreviewTranslationsViewModel(),
            mainController = PreviewIMainController(),
        )
    }
}