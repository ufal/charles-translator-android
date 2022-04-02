package cz.uk.lindat.main

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import cz.uk.lindat.R
import cz.uk.lindat.main.ui.MainScreen
import cz.uk.lindat.main.viewActions.IMainViewActions
import cz.uk.lindat.main.viewmodel.MainViewModel

class MainActivity : ComponentActivity(), IMainViewActions {

    private val viewModel: MainViewModel by viewModels()

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
}

