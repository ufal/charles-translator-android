package cz.cuni.mff.ufal.translator.interactors.api.data

import kotlinx.serialization.Serializable

/**
 * @author Tomas Krabac
 */
@Serializable
data class NotImplementedData(val title: String, val message: String) {
}