package by.vasilevskiy.dota2analytics.api

import by.vasilevskiy.dota2analytics.data.Team
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface TeamApi {
    @GET("api/teams")
    fun getTeams(): Call<List<Team>>
}

internal fun provideApi(): TeamApi {
    val baseUrl = "https://api.opendota.com/"

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    return retrofit.create(TeamApi::class.java)
}