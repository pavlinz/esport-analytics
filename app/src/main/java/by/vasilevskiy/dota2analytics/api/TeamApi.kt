package by.vasilevskiy.dota2analytics.api

import by.vasilevskiy.dota2analytics.data.Team
import by.vasilevskiy.dota2analytics.utils.Constants.Companion.BASE_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface TeamApi {
    @GET("api/teams")
    fun getTeams(): Call<List<Team>>

    /*@GET("api/teams/{teamId}")
    fun getSpecificTeam(@Path("id") teamId: String): Call<Team>*/
}

internal fun provideApi(): TeamApi {

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    return retrofit.create(TeamApi::class.java)
}