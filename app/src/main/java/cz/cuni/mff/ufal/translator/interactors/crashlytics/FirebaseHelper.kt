package cz.cuni.mff.ufal.translator.interactors.crashlytics

import android.content.Context
import android.speech.SpeechRecognizer
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.crashlytics.ktx.setCustomKeys
import com.google.firebase.ktx.Firebase
import java.util.*

/**
 * @author Tomas Krabac
 */
object FirebaseHelper {

    fun setBaseFirebaseInfo(context: Context) {
        val locale = Locale.getDefault().language
        val isRecognitionAvailable = SpeechRecognizer.isRecognitionAvailable(context)


        Firebase.crashlytics.setCustomKeys {
            key(FirebaseConstants.PARAM_USER_LOCALE, locale)
            key(FirebaseConstants.PARAM_IS_RECOGNITION_AVAILABLE, isRecognitionAvailable)
        }

        Firebase.analytics.apply {
            setUserProperty(FirebaseConstants.PARAM_USER_LOCALE, locale)
            setUserProperty(FirebaseConstants.PARAM_IS_RECOGNITION_AVAILABLE, isRecognitionAvailable.toString())
        }
    }

    fun setFirebaseScreen(screen: Screen) {
        Firebase.crashlytics.setCustomKey(FirebaseConstants.PARAM_SCREEN, screen.value)

        Firebase.analytics.logEvent(
            FirebaseAnalytics.Event.SCREEN_VIEW,
            bundleOf(
                FirebaseAnalytics.Param.SCREEN_NAME to screen.value
            )
        )
    }
}