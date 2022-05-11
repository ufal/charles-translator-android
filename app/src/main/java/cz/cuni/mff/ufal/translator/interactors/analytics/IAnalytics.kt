package cz.cuni.mff.ufal.translator.interactors.analytics

import android.content.Context
import cz.cuni.mff.ufal.translator.interactors.analytics.events.SpeechToTextEvent
import cz.cuni.mff.ufal.translator.interactors.analytics.events.TextToSpeechEvent
import cz.cuni.mff.ufal.translator.interactors.analytics.events.TranslateEvent

/**
 * @author Tomas Krabac
 */
interface IAnalytics {

    fun setBaseInfo(context: Context)

    fun logEvent(event: TranslateEvent)

    fun logEvent(event: TextToSpeechEvent)

    fun logEvent(event: SpeechToTextEvent)

}