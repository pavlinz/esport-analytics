package by.vasilevskiy.dota2analytics.ui.teams.repo

import android.util.Log
import by.vasilevskiy.dota2analytics.data.network.provideApi
import by.vasilevskiy.dota2analytics.models.Match
import by.vasilevskiy.dota2analytics.models.Player
import by.vasilevskiy.dota2analytics.models.Team
import javax.inject.Inject

class DefaultTeamsRepository @Inject constructor() :
    TeamsRepository {

    private val TAG = "TeamsRepository"

    override suspend fun getTeams() : List<Team>? {
        var teams: List<Team>? = null
        try {
            teams = provideApi().getTeams()
        } catch (exception: Exception) {
            Log.d(TAG, exception.message.toString())
        }
        return teams
    }

    override suspend fun getTeamPlayers(id: Int) : List<Player> {
        lateinit var players: List<Player>
        try {
            players = provideApi().getTeamPlayers(id)
        } catch (exception: Exception) {
            Log.d(TAG, exception.message.toString())
        }
        return players
    }

    override suspend fun getTeamMatches(id: Int) : List<Match> {
        lateinit var matches: List<Match>
        try {
            matches = provideApi().getTeamMatches(id)
        } catch (exception: Exception) {
            Log.d(TAG, exception.message.toString())
        }
        return matches
    }
}