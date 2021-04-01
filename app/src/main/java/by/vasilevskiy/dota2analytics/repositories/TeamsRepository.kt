package by.vasilevskiy.dota2analytics.repositories

import by.vasilevskiy.dota2analytics.api.provideApi

class TeamsRepository {

    suspend fun getTeams() = provideApi().getTeams()

    suspend fun getSpecificTeam(id: Int) = provideApi().getSpecificTeam(id)

    suspend fun getTeamPlayers(id: Int) = provideApi().getTeamPlayers(id)

    suspend fun getTeamMatches(id: Int) = provideApi().getTeamMatches(id)
}