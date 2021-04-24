package by.vasilevskiy.dota2analytics.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vote: Vote)

    @Query("SELECT * FROM VoteTable WHERE name == :name")
    fun getVoteById(name: String): Vote

    @Query("DELETE FROM VoteTable")
    suspend fun deleteAll()

    @Query("SELECT * FROM VoteTable")
    suspend fun getAll(): List<Vote>

}