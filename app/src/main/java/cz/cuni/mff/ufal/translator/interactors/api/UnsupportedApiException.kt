package cz.cuni.mff.ufal.translator.interactors.api

import cz.cuni.mff.ufal.translator.interactors.api.data.NotImplementedData

/**
 * @author Tomas Krabac
 */
data class UnsupportedApiException(val data: NotImplementedData) : Exception()