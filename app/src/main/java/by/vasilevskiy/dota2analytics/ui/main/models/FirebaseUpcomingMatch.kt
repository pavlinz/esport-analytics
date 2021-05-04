package by.vasilevskiy.dota2analytics.ui.main.models

data class FirebaseUpcomingMatch(
    var name: String = "",
    var countFirstTeamVotes: Int = 0,
    var countSecondTeamVotes: Int = 0
) {
    override fun toString(): String {
        return "FirebaseUpcomingMatch(name='$name', countFirstTeamVotes=$countFirstTeamVotes, countSecondTeamVotes=$countSecondTeamVotes)"
    }
}