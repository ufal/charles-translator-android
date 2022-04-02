package cz.cuni.mff.lindat.db.history

import androidx.room.*
import cz.cuni.mff.lindat.history.data.HistoryItem
import kotlinx.coroutines.flow.Flow

/**
 * @author Tomas Krabac
 */
@Dao
interface IHistoryDao {
    @Query("SELECT * FROM history_items ORDER BY is_favourite DESC")
    fun getAll(): Flow<List<HistoryItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: HistoryItem)

    @Delete
    fun delete(item: HistoryItem)

    @Update
    fun update(item: HistoryItem)
}