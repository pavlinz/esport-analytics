package by.vasilevskiy.dota2analytics.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "VoteTable")
data class Vote(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val team: Int,
) {
    override fun toString(): String {
        return "Vote(id=$id, name='$name', team=$team)"
    }
}

enum class VotingOptions {

    FIRST,

    SECOND

}