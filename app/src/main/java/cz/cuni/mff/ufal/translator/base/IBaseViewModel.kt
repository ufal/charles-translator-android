package cz.cuni.mff.ufal.translator.base

/**
 * @author Tomas Krabac
 */
interface IBaseViewModel {

    fun onStart(): Unit = Unit

    fun onStop(): Unit = Unit
}