package cz.cuni.mff.ufal.translator.ui.history.screens.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.main.controller.IMainController
import cz.cuni.mff.ufal.translator.ui.history.model.HistoryItem
import cz.cuni.mff.ufal.translator.ui.history.viewmodel.IHistoryViewModel
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme
import cz.cuni.mff.ufal.translator.ui.translations.models.Language

/**
 * @author Tomas Krabac
 */

@Composable
fun HistoryCard(
    item: HistoryItem,
    viewModel: IHistoryViewModel,
    mainController: IMainController,
) {
    val isTextToSpeechAvailable by viewModel.isTextToSpeechAvailable.collectAsState()

    HistoryCard(
        item = item,
        isTextToSpeechAvailable = isTextToSpeechAvailable,

        onRowClicked = { mainController.navigateMainScreen(item) },
        onDeleteClicked = { viewModel.deleteItem(item) },
        onFavouriteClicked = {
            val updatedItem = item.copy(isFavourite = !item.isFavourite)
            viewModel.updateItem(updatedItem)
        },
        copyToClipBoardClicked = { viewModel.copyToClipBoard(item.outputText) },
        textToSpeechClicked = { viewModel.textToSpeech(item) }
    )
}

@Composable
private fun HistoryCard(
    item: HistoryItem,
    isTextToSpeechAvailable: Boolean,

    onRowClicked: () -> Unit,
    onFavouriteClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    copyToClipBoardClicked: () -> Unit,
    textToSpeechClicked: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable(onClick = onRowClicked),
        elevation = 8.dp
    ) {
        Column {

            Box {

                DeleteItem(modifier = Modifier.align(Alignment.TopEnd)) {
                    onDeleteClicked()
                }

                TextContent(
                    modifier = Modifier.align(Alignment.TopStart),
                    item = item,
                )
            }


            Spacer(modifier = Modifier.height(8.dp))

            ActionsRow(
                isTextToSpeechAvailable = isTextToSpeechAvailable,
                isFavourite = item.isFavourite,
                onFavouriteClicked = onFavouriteClicked,
                copyToClipBoard = copyToClipBoardClicked,
                textToSpeech = textToSpeechClicked,
            )
        }
    }
}

@Composable
private fun DeleteItem(modifier: Modifier, onClick: () -> Unit) {

    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_delete),
            tint = MaterialTheme.colors.primary,
            contentDescription = stringResource(id = R.string.delete_cd),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryCardPreview() {
    LindatTheme {
        HistoryCard(
            item = HistoryItem("test", "preklad", Language.Czech, Language.Ukrainian, false),
            isTextToSpeechAvailable = true,

            onRowClicked = {},
            onFavouriteClicked = {},
            onDeleteClicked = {},
            copyToClipBoardClicked = {},
            textToSpeechClicked = {},
        )
    }
}
