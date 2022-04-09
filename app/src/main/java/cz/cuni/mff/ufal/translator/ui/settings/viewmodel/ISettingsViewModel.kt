package cz.cuni.mff.ufal.translator.ui.settings.viewmodel

import cz.cuni.mff.ufal.translator.base.IBaseViewModel
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Tomas Krabac
 */
interface ISettingsViewModel: IBaseViewModel {

    val agreeWithDataCollection: StateFlow<Boolean>

    fun saveAgreementDataCollection(agree: Boolean)

}