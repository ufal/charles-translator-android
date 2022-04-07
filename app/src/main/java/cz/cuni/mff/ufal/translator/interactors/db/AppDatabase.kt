package cz.cuni.mff.ufal.translator.interactors.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cz.cuni.mff.ufal.translator.history.data.HistoryItem
import cz.cuni.mff.ufal.translator.interactors.db.history.IHistoryDao

/**
 * @author Tomas Krabac
 */
@Database(entities = [HistoryItem::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): IHistoryDao
}