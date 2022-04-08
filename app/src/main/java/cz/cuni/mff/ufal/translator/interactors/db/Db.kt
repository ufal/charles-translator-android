package cz.cuni.mff.ufal.translator.interactors.db

import android.content.Context
import androidx.room.Room

/**
 * @author Tomas Krabac
 */
class Db(applicationContext: Context) : IDb {

    private val db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java,
        DB_NAME
    )
        .fallbackToDestructiveMigration() // TODO: later use mirgrations
        .build()

    override val historyDao = db.historyDao()

    companion object{
        const val DB_NAME = "lindat"
        const val DB_VERSION = 2
    }
}