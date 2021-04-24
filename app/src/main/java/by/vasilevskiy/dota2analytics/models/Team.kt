package by.vasilevskiy.dota2analytics.models

data class Team (
    val last_match_time: Int,
    val logo_url: String,
    val losses: Int,
    val name: String,
    val rating: Double,
    val tag: String,
    val team_id: Int,
    val wins: Int
)