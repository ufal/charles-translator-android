package cz.cuni.mff.ufal.translator.extensions

import android.content.Context
import cz.cuni.mff.ufal.translator.R

/**
 * @author Tomas Krabac
 */
val Context.isTablet get() = resources.getBoolean(R.bool.is_tablet)