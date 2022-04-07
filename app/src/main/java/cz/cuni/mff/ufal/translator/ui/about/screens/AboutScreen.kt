package cz.cuni.mff.ufal.translator.ui.about.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ireward.htmlcompose.HtmlText
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.base.BaseScreen
import cz.cuni.mff.ufal.translator.main.controller.IController
import cz.cuni.mff.ufal.translator.main.controller.PreviewIController
import cz.cuni.mff.ufal.translator.ui.common.widgets.BaseToolbar
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme

/**
 * @author Tomas Krabac
 */

private const val SUPPORT_MAIL = "u4u@ufal.mff.cuni.cz"

@Composable
fun AboutScreen(controller: IController) {
    BaseScreen {
        Content(
            controller = controller,
        )
    }
}

@Composable
private fun Content(controller: IController) {
    Column {
        BaseToolbar(titleRes = R.string.about_title) {
            controller.onBackPressed()
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier.height(56.dp),
                    painter = painterResource(id = R.drawable.img_ufal),
                    contentDescription = stringResource(id = R.string.ufal_cd),
                )

                Spacer(modifier = Modifier.width(8.dp))

                Image(
                    modifier = Modifier.height(56.dp),
                    painter = painterResource(id = R.drawable.img_lindat),
                    contentDescription = stringResource(id = R.string.lindat_cd),
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            HtmlText(
                text = stringResource(id = R.string.about_text, SUPPORT_MAIL),
                URLSpanStyle = SpanStyle(
                    color = MaterialTheme.colors.primary,
                    textDecoration = TextDecoration.Underline
                ),
                linkClicked = { url ->
                    if (url == "/support-mail") {
                        controller.sendMail(SUPPORT_MAIL)
                    } else {
                        controller.openWebUrl(url)
                    }
                }
            )
        }


    }
}


@Preview(showBackground = true)
@Composable
private fun AboutScreenPreview() {
    LindatTheme {
        AboutScreen(
            controller = PreviewIController(),
        )
    }
}
