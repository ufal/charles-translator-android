package cz.cuni.mff.lindat.extensions

import android.util.Log

/**
 * @author Tomas Krabac
 */
fun Any.logV(message: String) {
    Log.v(this.javaClass.simpleName, message)
}

fun Any.logD(message: String) {
    Log.d(this.javaClass.simpleName, message)
}

fun Any.logE(message: String) {
    Log.e(this.javaClass.simpleName, message)
}

fun Any.logE(message: String, throwable: Throwable) {
    Log.e(this.javaClass.simpleName, message, throwable)
}
