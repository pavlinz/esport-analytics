package by.vasilevskiy.dota2analytics.data

data class Match(
    val cluster: Int,
    val duration: Int,
    val league_name: String,
    val leagueid: Int,
    val match_id: Long,
    val opposing_team_id: Int,
    val opposing_team_logo: String,
    val opposing_team_name: String,
    val radiant: Boolean,
    val radiant_win: Boolean,
    val start_time: Int
)