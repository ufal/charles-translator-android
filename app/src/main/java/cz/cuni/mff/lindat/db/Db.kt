package cz.cuni.mff.lindat.db

import android.content.Context
import androidx.room.Room

/**
 * @author Tomas Krabac
 */
class Db(applicationContext: Context) : IDb {

    private val db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "lindat"
    ).build()

    override val historyDao = db.historyDao()
}