package cz.cuni.mff.ufal.translator.interactors

import android.content.*
import android.net.Uri
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

    fun openGooglePlay(context: Context, packageName: String) {
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
        } catch (e: ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                )
            )
        }
    }

}