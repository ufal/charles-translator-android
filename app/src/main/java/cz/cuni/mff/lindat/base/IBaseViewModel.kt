package cz.cuni.mff.lindat.base

/**
 * @author Tomas Krabac
 */
interface IBaseViewModel {

    fun onStart(): Unit = Unit

    fun onStop(): Unit = Unit
}