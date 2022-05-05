package cz.cuni.mff.ufal.translator.interactors.analytics

import android.content.Context
import android.speech.SpeechRecognizer
import android.util.Log
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.crashlytics.ktx.setCustomKeys
import com.google.firebase.ktx.Firebase
import cz.cuni.mff.ufal.translator.extensions.logD
import cz.cuni.mff.ufal.translator.interactors.analytics.events.TranslateEvent
import cz.cuni.mff.ufal.translator.interactors.crashlytics.FirebaseConstants
import cz.cuni.mff.ufal.translator.interactors.preferences.IUserDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject

/**
 * @author Tomas Krabac
 */
class Analytics @Inject constructor(
    private val userDataStore: IUserDataStore,
) : IAnalytics {

    private val firebaseAnalytics get() = Firebase.analytics

    private val language = Locale.getDefault().language

    override fun setBaseInfo(context: Context) {
        val isRecognitionAvailable = SpeechRecognizer.isRecognitionAvailable(context)
        val agreeWithDataCollection = runBlocking { userDataStore.agreeWithDataCollection.first() }
        val darkMode = runBlocking { userDataStore.darkModeSetting.first() }

        Firebase.crashlytics.setCustomKeys {
            key(FirebaseConstants.PARAM_USER_LOCALE, language)
            key(FirebaseConstants.PARAM_IS_RECOGNITION_AVAILABLE, isRecognitionAvailable)
            key(FirebaseConstants.AGREE_WITH_DATA_COLLECTION, agreeWithDataCollection)
            key(FirebaseConstants.DARK_MODE, darkMode.key)
        }

        Firebase.analytics.apply {
            setUserProperty(FirebaseConstants.PARAM_USER_LOCALE, language)
            setUserProperty(FirebaseConstants.PARAM_IS_RECOGNITION_AVAILABLE, isRecognitionAvailable.toString())
            setUserProperty(FirebaseConstants.AGREE_WITH_DATA_COLLECTION, agreeWithDataCollection.toString())
            setUserProperty(FirebaseConstants.DARK_MODE, darkMode.key)
        }
    }

    override fun logEvent(event: TranslateEvent) {
        logD("TranslateEvent: $event")

        firebaseAnalytics.logEvent("tranlation") {
            param("input_language", event.inputLanguage.code)
            param("output_language", event.outputLanguage.code)
            param("languages", "${event.inputLanguage.code}@${event.outputLanguage.code}")
            param("text_lenght", event.data.text.length.toString())
            param("text_source", event.data.source.key)
            param("user_locale", language)
        }
    }

}