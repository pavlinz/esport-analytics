package by.vasilevskiy.dota2analytics.ui.teams.repo

import by.vasilevskiy.dota2analytics.models.Match
import by.vasilevskiy.dota2analytics.models.Player
import by.vasilevskiy.dota2analytics.models.Team

interface TeamsRepository {

    suspend fun getTeams() : List<Team>?

    suspend fun getTeamPlayers(id: Int) : List<Player>

    suspend fun getTeamMatches(id: Int) : List<Match>

}