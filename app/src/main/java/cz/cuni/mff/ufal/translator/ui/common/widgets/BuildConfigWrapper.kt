package cz.cuni.mff.ufal.translator.ui.common.widgets

import cz.cuni.mff.ufal.translator.BuildConfig

/**
 * @author Tomas Krabac
 */
object BuildConfigWrapper {

    val isDebug = BuildConfig.BUILD_TYPE == "debug"

    val isBeta = BuildConfig.BUILD_TYPE == "beta"

    val isRelease = BuildConfig.BUILD_TYPE == "release"
}