package cz.cuni.mff.ufal.translator.interactors.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cz.cuni.mff.ufal.translator.interactors.db.Db.Companion.DB_VERSION
import cz.cuni.mff.ufal.translator.ui.history.model.HistoryItem

/**
 * @author Tomas Krabac
 */
@Database(entities = [HistoryItem::class], version = DB_VERSION)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): IHistoryDao
}