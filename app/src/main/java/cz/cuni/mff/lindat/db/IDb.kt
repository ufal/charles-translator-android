package cz.cuni.mff.lindat.db

import cz.cuni.mff.lindat.db.history.IHistoryDao

/**
 * @author Tomas Krabac
 */
interface IDb {
    val historyDao: IHistoryDao
}