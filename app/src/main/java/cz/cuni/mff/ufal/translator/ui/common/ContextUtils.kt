package cz.cuni.mff.ufal.translator.ui.common

import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import cz.cuni.mff.ufal.translator.R

/**
 * @author Tomas Krabac
 */
object ContextUtils {

    fun copyToClipBoard(context: Context, label: String, text: String) {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.setPrimaryClip(ClipData.newPlainText(label, text))

        Toast.makeText(context, R.string.toast_copied_to_clipboard, Toast.LENGTH_SHORT).show()
    }

    fun pasteFromClipBoard(context: Context) : String {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        clipboard ?: return ""

        return if (clipboard.primaryClipDescription?.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN) == true) {
            clipboard.primaryClip?.getItemAt(0)?.text?.toString() ?: ""
        } else{
            ""
        }
    }

}