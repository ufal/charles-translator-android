package cz.cuni.mff.ufal.translator.ui.history.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.base.BaseScreen
import cz.cuni.mff.ufal.translator.main.controller.IMainController
import cz.cuni.mff.ufal.translator.main.controller.PreviewIMainController
import cz.cuni.mff.ufal.translator.ui.common.widgets.BaseToolbar
import cz.cuni.mff.ufal.translator.ui.history.screens.widgets.HistoryCard
import cz.cuni.mff.ufal.translator.ui.history.viewmodel.IHistoryViewModel
import cz.cuni.mff.ufal.translator.ui.history.viewmodel.PreviewHistoryViewModel
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme

/**
 * @author Tomas Krabac
 */
@Composable
fun HistoryFavouritesScreen(viewModel: IHistoryViewModel, mainController: IMainController) {
    BaseScreen(viewModel = viewModel) {
        Content(
            viewModel = viewModel,
            mainController = mainController,
        )
    }
}

@Composable
private fun Content(viewModel: IHistoryViewModel, mainController: IMainController) {
    val historyItems by viewModel.favouritesItems.collectAsState()

    Column {
        BaseToolbar(titleRes = R.string.history_title) {
            mainController.onBackPressed()
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            state = rememberLazyListState(),
        ) {
            historyItems.forEachIndexed { index, historyItem ->
                item(historyItem.insertedMS) {
                    Column(modifier = Modifier.animateItemPlacement()) {
                        if (index == 0) {
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        HistoryCard(
                            item = historyItem,
                            viewModel = viewModel,
                            mainController = mainController,
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryFavouritesScreenPreview() {
    LindatTheme {
        HistoryFavouritesScreen(
            viewModel = PreviewHistoryViewModel(),
            mainController = PreviewIMainController(),
        )
    }
}