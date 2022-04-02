package cz.cuni.mff.lindat.main

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cz.cuni.mff.lindat.R
import cz.cuni.mff.lindat.history.ui.HistoryScreen
import cz.cuni.mff.lindat.history.viewmodel.HistoryViewModel
import cz.cuni.mff.lindat.history.viewmodel.IHistoryViewModel
import cz.cuni.mff.lindat.main.controller.rememberController
import cz.cuni.mff.lindat.main.ui.MainScreen
import cz.cuni.mff.lindat.main.viewActions.IMainViewActions
import cz.cuni.mff.lindat.main.viewmodel.MainViewModel
import cz.cuni.mff.lindat.voice.VoiceContract
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), IMainViewActions {

    private val mainViewModel: MainViewModel by viewModels()
    private val historyViewModel: IHistoryViewModel by viewModels<HistoryViewModel>()

    private val voiceLauncher = registerForActivityResult(
        VoiceContract(), ::onVoiceResult
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val controller = rememberController()

            NavHost(controller.navController, startDestination = "main") {
                composable("main") {
                    MainScreen(
                        viewModel = mainViewModel,
                        viewActions = this@MainActivity,
                        controller = controller
                    )
                }
                composable("history") {
                    HistoryScreen(
                        viewModel = historyViewModel,
                        controller = controller
                    )
                }
            }
        }
    }

    override fun copyToClipBoard(label: String, text: String) {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.setPrimaryClip(ClipData.newPlainText(label, text))

        Toast.makeText(this, R.string.toast_copied_to_clipboard, Toast.LENGTH_SHORT).show()
    }

    override fun startSpeechToText() {
        voiceLauncher.launch()
    }

    private fun onVoiceResult(result: String?) {
        if (result != null) {
            mainViewModel.setInputText(result)
        }
    }
}

