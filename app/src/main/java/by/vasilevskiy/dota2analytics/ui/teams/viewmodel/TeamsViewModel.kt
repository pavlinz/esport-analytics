package by.vasilevskiy.dota2analytics.ui.teams

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.vasilevskiy.dota2analytics.api.provideApi
import by.vasilevskiy.dota2analytics.data.Team
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamsViewModel: ViewModel() {

    init {
        getTeams()
    }

    val result = MutableLiveData<List<Team>>()
    val selected = MutableLiveData<Team>()

    fun select(position: Int) {
        selected.value = result.value?.get(position)
    }

    private fun getTeams() {
        provideApi().getTeams().enqueue(object : Callback<List<Team>> {
            override fun onResponse(
                call: Call<List<Team>>,
                response: Response<List<Team>>
            ) {
                if (response.isSuccessful) {
                    result.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<Team>>, t: Throwable) {
                Log.e("MainViewModel", t.message.toString())
            }
        })
    }
}