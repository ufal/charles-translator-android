package cz.cuni.mff.ufal.translator.ui.common.widgets

import cz.cuni.mff.ufal.translator.BuildConfig

/**
 * @author Tomas Krabac
 */
object BuildConfigWrapper {
    val isRelease = BuildConfig.BUILD_TYPE == "release"
}