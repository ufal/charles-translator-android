package cz.cuni.mff.ufal.translator.interactors.crashlytics

import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase

/**
 * @author Tomas Krabac
 */
object FirebaseHelper {

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