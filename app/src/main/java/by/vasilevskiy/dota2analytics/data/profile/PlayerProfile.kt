package by.vasilevskiy.dota2analytics.data.profile

data class PlayerProfile(
    val competitive_rank: String,
    val leaderboard_rank: Int?,
    val mmr_estimate: MmrEstimate,
    val profile: Profile,
    val rank_tier: Int,
    val solo_competitive_rank: String,
    val tracked_until: String
)