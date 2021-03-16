package by.vasilevskiy.dota2analytics.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import by.vasilevskiy.dota2analytics.api.provideApi
import by.vasilevskiy.dota2analytics.data.Team
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamsRepository {

    fun getTeams(): List<Team> {

        lateinit var responseApi : List<Team>

        provideApi().getTeams().enqueue(object : Callback<List<Team>> {
            override fun onResponse(
                call: Call<List<Team>>,
                response: Response<List<Team>>
            ) {
                if (response.isSuccessful) {
                    responseApi = response.body()!!
                }
            }

            override fun onFailure(call: Call<List<Team>>, t: Throwable) {
                responseApi = emptyList()
            }
        })
        return responseApi
    }
}