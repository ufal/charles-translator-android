package cz.cuni.mff.ufal.translator.ui.settings.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.base.BaseScreen
import cz.cuni.mff.ufal.translator.main.controller.IMainController
import cz.cuni.mff.ufal.translator.main.controller.PreviewIMainController
import cz.cuni.mff.ufal.translator.ui.common.widgets.BaseToolbar
import cz.cuni.mff.ufal.translator.ui.settings.screens.widgets.SettingItem
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

    Column {
        BaseToolbar(titleRes = R.string.settings_title) {
            mainController.onBackPressed()
        }


        SettingItem(
            titleRes = R.string.settings_data_collection_title,
            descriptionRes = R.string.settings_data_collection_description,
            isChecked = agreeWithDataCollection,

            onCheckedChange = viewModel::saveAgreementDataCollection
        )


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



