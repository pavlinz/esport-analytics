package by.vasilevskiy.dota2analytics.models

class UpcomingMatch(
    val leagueName: String,
    val leagueLogo: String,
    val firstTeamName: String,
    val secondTeamName: String,
    val firstTeamLogo: String,
    val secondTeamLogo: String,
    val versus: String,
    val matchTime: String
) {
    companion object {
        val URL = "https://liquipedia.net/commons/images/2/2f/Dotalogo_std.png"
    }

    override fun toString(): String {
        return "UpcomingMatch(leagueName='$leagueName', leagueLogo='$leagueLogo', firstTeamName='$firstTeamName', secondTeamName='$secondTeamName', firstTeamLogo='$firstTeamLogo', secondTeamLogo='$secondTeamLogo', matchTime='$matchTime')"
    }
}