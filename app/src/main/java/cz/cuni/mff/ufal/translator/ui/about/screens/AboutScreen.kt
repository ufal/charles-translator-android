package cz.cuni.mff.ufal.translator.ui.about.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ireward.htmlcompose.HtmlText
import com.ramcosta.composedestinations.annotation.Destination
import cz.cuni.mff.ufal.translator.BuildConfig
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.base.BaseScreen
import cz.cuni.mff.ufal.translator.interactors.crashlytics.Screen
import cz.cuni.mff.ufal.translator.main.controller.IMainController
import cz.cuni.mff.ufal.translator.main.controller.PreviewIMainController
import cz.cuni.mff.ufal.translator.ui.common.widgets.BaseToolbar
import cz.cuni.mff.ufal.translator.ui.theme.LindatTheme
import cz.cuni.mff.ufal.translator.ui.theme.LindatThemePreview

/**
 * @author Tomas Krabac
 */

private const val SUPPORT_MAIL = "translator@ufal.mff.cuni.cz"

@Destination()
@Composable
fun AboutScreen(mainController: IMainController) {
    BaseScreen(screen = Screen.About, darkModeSetting = mainController.darkModeSetting) {
        Content(
            mainController = mainController,
        )
    }
}

@Composable
private fun Content(mainController: IMainController) {
    Column {
        BaseToolbar(titleRes = R.string.about_title) {
            mainController.onBackPressed()
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            AppVersionItem()

            Spacer(modifier = Modifier.height(8.dp))

            HtmlTextItem(
                text = stringResource(id = R.string.about_text_intro),
                mainController = mainController
            )

            Spacer(modifier = Modifier.height(16.dp))

            LogoItem()

            Spacer(modifier = Modifier.height(16.dp))

            HtmlTextItem(
                text = stringResource(id = R.string.about_text, SUPPORT_MAIL),
                mainController = mainController
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun AppVersionItem() {
    val appName = stringResource(id = R.string.app_name)
    val versionCode = BuildConfig.VERSION_CODE
    val versionName = BuildConfig.VERSION_NAME
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "$appName \n $versionName ($versionCode)",
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun LogoItem() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val imageHeight = 88.dp
        Image(
            modifier = Modifier
                .height(imageHeight)
                .background(Color.White)
                .padding(horizontal = 8.dp),
            painter = painterResource(id = R.drawable.img_ufal),
            contentDescription = stringResource(id = R.string.ufal_cd),
        )

        Spacer(modifier = Modifier.width(16.dp))

        Image(
            modifier = Modifier
                .height(imageHeight)
                .background(Color.White),
            painter = painterResource(id = R.drawable.img_lindat),
            contentDescription = stringResource(id = R.string.lindat_cd),
        )
    }
}


@Composable
private fun HtmlTextItem(
    text: String,
    mainController: IMainController,
) {
    HtmlText(
        text = text,
        style = TextStyle(
            color = LindatTheme.colors.onSurface,
        ),
        URLSpanStyle = SpanStyle(
            color = LindatTheme.colors.primary,
            textDecoration = TextDecoration.Underline
        ),
        linkClicked = { url ->
            if (url == "/support-mail") {
                mainController.sendMail(SUPPORT_MAIL)
            } else {
                mainController.openWebUrl(url)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun AboutScreenPreview() {
    LindatThemePreview {
        AboutScreen(
            mainController = PreviewIMainController(),
        )
    }
}
