package by.vasilevskiy.dota2analytics.api

import by.vasilevskiy.dota2analytics.data.Match
import by.vasilevskiy.dota2analytics.data.Player
import by.vasilevskiy.dota2analytics.data.Team
import by.vasilevskiy.dota2analytics.data.profile.PlayerProfile
import by.vasilevskiy.dota2analytics.utils.Constants.Companion.BASE_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface TeamApi {
    @GET("api/teams")
    suspend fun getTeams(): List<Team>

    @GET("api/teams/{teamId}")
    suspend fun getSpecificTeam(@Path("id") teamId: Int): Team

    @GET("api/teams/{teamId}/players")
    suspend fun getTeamPlayers(@Path("teamId") teamId: Int): List<Player>

    @GET("api/players/{account_id}")
    suspend fun getPlayerData(@Path("account_id") accountId: Int): PlayerProfile

    @GET("api/teams/{teamId}/matches")
    suspend fun getTeamMatches(@Path("teamId") teamId: Int): List<Match>
}

internal fun provideApi(): TeamApi {

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    return retrofit.create(TeamApi::class.java)
}