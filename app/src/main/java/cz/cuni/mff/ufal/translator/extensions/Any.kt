package cz.cuni.mff.ufal.translator.extensions

import android.util.Log
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import cz.cuni.mff.ufal.translator.ui.common.widgets.BuildConfigWrapper.isDebug

/**
 * @author Tomas Krabac
 */
fun Any.logV(message: String) {
    if (isDebug) {
        Log.v(this.javaClass.simpleName, message)
        Firebase.crashlytics.log("V:$message")
    }
}

fun Any.logD(message: String) {
    if (isDebug) {
        Log.d(this.javaClass.simpleName, message)
        Firebase.crashlytics.log("D:$message")
    }
}

fun Any.logE(message: String) {
    Log.e(this.javaClass.simpleName, message)
    Firebase.crashlytics.log("E:$message")
}

fun Any.logE(message: String, throwable: Throwable) {
    Log.e(this.javaClass.simpleName, message, throwable)
    Firebase.crashlytics.log("E:$message")
    Firebase.crashlytics.recordException(throwable)
}
