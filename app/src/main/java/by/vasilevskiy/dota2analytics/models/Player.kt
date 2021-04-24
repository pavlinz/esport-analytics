package by.vasilevskiy.dota2analytics.models

data class Player(
    val account_id: Int,
    val games_played: Int,
    val is_current_team_member: Boolean?,
    val name: String?,
    val wins: Int
)