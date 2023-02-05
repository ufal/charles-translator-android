package cz.cuni.mff.ufal.translator.interactors.analytics

import android.content.Context
import android.content.res.Configuration
import android.speech.SpeechRecognizer
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.crashlytics.ktx.setCustomKeys
import com.google.firebase.ktx.Firebase
import cz.cuni.mff.ufal.translator.extensions.logD
import cz.cuni.mff.ufal.translator.interactors.analytics.events.ConverstationEvent
import cz.cuni.mff.ufal.translator.interactors.analytics.events.SpeechToTextEvent
import cz.cuni.mff.ufal.translator.interactors.analytics.events.TextToSpeechEvent
import cz.cuni.mff.ufal.translator.interactors.analytics.events.TranslateEvent
import cz.cuni.mff.ufal.translator.interactors.crashlytics.FirebaseConstants
import cz.cuni.mff.ufal.translator.interactors.preferences.IUserDataStore
import cz.cuni.mff.ufal.translator.interactors.preferences.data.DarkModeSetting
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
        val darkModeSettings = runBlocking { userDataStore.darkModeSetting.first() }
        val darkModeValue = when(darkModeSettings){
            DarkModeSetting.System -> context.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
            DarkModeSetting.Enabled -> true
            DarkModeSetting.Disabled -> false
        }

        Firebase.crashlytics.setCustomKeys {
            key(FirebaseConstants.PARAM_USER_LOCALE, language)
            key(FirebaseConstants.PARAM_IS_RECOGNITION_AVAILABLE, isRecognitionAvailable)
            key(FirebaseConstants.AGREE_WITH_DATA_COLLECTION, agreeWithDataCollection)
            key(FirebaseConstants.DARK_MODE_SETTINGS, darkModeSettings.key)
            key(FirebaseConstants.DARK_MODE_VALUE, darkModeValue.toString())
        }

        Firebase.analytics.apply {
            setUserProperty(FirebaseConstants.PARAM_USER_LOCALE, language)
            setUserProperty(FirebaseConstants.PARAM_IS_RECOGNITION_AVAILABLE, isRecognitionAvailable.toString())
            setUserProperty(FirebaseConstants.AGREE_WITH_DATA_COLLECTION, agreeWithDataCollection.toString())
            setUserProperty(FirebaseConstants.DARK_MODE_SETTINGS, darkModeSettings.key)
            setUserProperty(FirebaseConstants.DARK_MODE_VALUE, darkModeValue.toString())
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

    override fun logEvent(event: TextToSpeechEvent) {
        logD("TextToSpeechEvent: $event")

        firebaseAnalytics.logEvent("text_to_speech") {
            param("output_language", event.language.code)
            param("text_lenght", event.text.length.toString())
            param("user_locale", language)
            param("screen", event.screen.key)
        }
    }

    override fun logEvent(event: SpeechToTextEvent) {
        logD("SpeechToTextEvent: $event")

        firebaseAnalytics.logEvent("speech_to_text") {
            param("input_language", event.language.code)
            param("text_lenght", event.text.length.toString())
            param("user_locale", language)
        }
    }

    override fun logEvent(event: ConverstationEvent) {
        logD("ConverstationEvent: $event")

        firebaseAnalytics.logEvent("conversation") {
            param("input_language", event.inputLanguage.code)
            param("output_language", event.outputLanguage.code)
            param("languages", "${event.inputLanguage.code}@${event.outputLanguage.code}")
            param("text_lenght", event.text.length.toString())
            param("user_locale", language)
        }
    }

}