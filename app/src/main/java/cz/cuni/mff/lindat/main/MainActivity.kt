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
import cz.cuni.mff.lindat.main.ui.MainScreen
import cz.cuni.mff.lindat.main.viewActions.IMainViewActions
import cz.cuni.mff.lindat.main.viewmodel.MainViewModel
import cz.cuni.mff.lindat.voice.VoiceContract
import cz.uk.lindat.R

class MainActivity : ComponentActivity(), IMainViewActions {

    private val viewModel: MainViewModel by viewModels()

    private val voiceLauncher = registerForActivityResult(
        VoiceContract(), ::onVoiceResult
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(viewModel, this)
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
            viewModel.setInputText(result)
        }
    }
}

