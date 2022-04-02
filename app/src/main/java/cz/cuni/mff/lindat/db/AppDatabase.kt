package cz.cuni.mff.lindat.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cz.cuni.mff.lindat.db.history.IHistoryDao
import cz.cuni.mff.lindat.history.data.HistoryItem

/**
 * @author Tomas Krabac
 */
@Database(entities = [HistoryItem::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): IHistoryDao
}