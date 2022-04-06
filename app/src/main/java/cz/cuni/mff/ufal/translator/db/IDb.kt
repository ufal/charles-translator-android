package cz.cuni.mff.ufal.translator.db

import cz.cuni.mff.ufal.translator.db.history.IHistoryDao

/**
 * @author Tomas Krabac
 */
interface IDb {
    val historyDao: IHistoryDao
}