package cz.cuni.mff.ufal.translator.ui.translations.models

import cz.cuni.mff.ufal.translator.interactors.api.data.NotImplementedData

/**
 * @author Tomas Krabac
 */
sealed class TranslationsScreenState {
    object Idle: TranslationsScreenState()
    object Loading: TranslationsScreenState()
    object Error: TranslationsScreenState()
    object MaxCharactersLimitError: TranslationsScreenState()
    data class UnSupportedApiError(val data: NotImplementedData): TranslationsScreenState()
    object Success: TranslationsScreenState()
    object Offline: TranslationsScreenState()
}