package cz.cuni.mff.ufal.translator.interactors.db

import cz.cuni.mff.ufal.translator.interactors.db.history.IHistoryDao

/**
 * @author Tomas Krabac
 */
interface IDb {
    val historyDao: IHistoryDao
}