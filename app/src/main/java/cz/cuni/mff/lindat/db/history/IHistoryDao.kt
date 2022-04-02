package cz.cuni.mff.lindat.db.history

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * @author Tomas Krabac
 */
@Dao
interface IHistoryDao {
    @Query("SELECT * FROM history_items ORDER BY is_favourite DESC")
    fun getAll(): Flow<List<HistoryItemDB>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: HistoryItemDB)

    @Delete
    fun delete(item: HistoryItemDB)

    @Update
    fun update(item: HistoryItemDB)
}