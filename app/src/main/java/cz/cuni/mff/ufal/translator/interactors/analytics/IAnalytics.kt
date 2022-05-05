package cz.cuni.mff.ufal.translator.interactors.analytics

import android.content.Context
import cz.cuni.mff.ufal.translator.interactors.analytics.events.TranslateEvent

/**
 * @author Tomas Krabac
 */
interface IAnalytics {

    fun setBaseInfo(context: Context)

    fun logEvent(event: TranslateEvent)

}