package cz.cuni.mff.ufal.translator.ui.settings.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.base.BaseScreen
import cz.cuni.mff.ufal.translator.main.controller.IMainController
import cz.cuni.mff.ufal.translator.main.controller.PreviewIMainController
import cz.cuni.mff.ufal.translator.ui.common.widgets.BaseToolbar
import cz.cuni.mff.ufal.translator.ui.settings.screens.widgets.SettingSingleItem
import cz.cuni.mff.ufal.translator.ui.settings.screens.widgets.SettingSwitchItem
import cz.cuni.mff.ufal.translator.ui.settings.screens.widgets.SingleSelectionDialog
import cz.cuni.mff.ufal.translator.ui.settings.viewmodel.ISettingsViewModel
import cz.cuni.mff.ufal.translator.ui.settings.viewmodel.PreviewSettingsViewModel
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme

/**
 * @author Tomas Krabac
 */
@Composable
fun SettingsScreen(viewModel: ISettingsViewModel, mainController: IMainController) {
    BaseScreen(viewModel) {
        Content(
            viewModel = viewModel,
            mainController = mainController,
        )
    }
}

@Composable
private fun Content(viewModel: ISettingsViewModel, mainController: IMainController) {
    val agreeWithDataCollection by viewModel.agreeWithDataCollection.collectAsState()
    val useNetworkTTS by viewModel.useNetworkTTS.collectAsState()
    val selectedTtsEngineName by viewModel.selectedTtsEngine.collectAsState()
    val ttsEngines by viewModel.engines.collectAsState()

    val selectedEngine = ttsEngines.find { it.name == selectedTtsEngineName }!!
    val dialogState = rememberMaterialDialogState()

    Column {
        BaseToolbar(titleRes = R.string.settings_title) {
            mainController.onBackPressed()
        }

        SettingSwitchItem(
            titleRes = R.string.settings_data_collection_title,
            descriptionRes = R.string.settings_data_collection_description,
            isChecked = agreeWithDataCollection,

            onCheckedChange = viewModel::saveAgreementDataCollection
        )

        if (ttsEngines.isNotEmpty()) {
            SettingSwitchItem(
                titleRes = R.string.settings_network_tts_title,
                descriptionRes = R.string.settings_network_tts_description,
                isChecked = useNetworkTTS,

                onCheckedChange = viewModel::saveUseNetworkTTS
            )
        }

        if (ttsEngines.size > 1) {
            SettingSingleItem(
                titleRes = R.string.settings_tts_engine_title,
                value = selectedEngine.label
            ) {
                dialogState.show()
            }
        }

        SingleSelectionDialog(
            dialogState = dialogState,
            title = stringResource(id = R.string.settings_tts_engine_title),
            selectedItem = selectedEngine.label,
            items = ttsEngines.map { it.label }
        ) { selectedIndex ->
            val engine = ttsEngines[selectedIndex]
            viewModel.saveTTSengine(engine.name)
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    LindatTheme {
        SettingsScreen(
            viewModel = PreviewSettingsViewModel(),
            mainController = PreviewIMainController(),
        )
    }
}



