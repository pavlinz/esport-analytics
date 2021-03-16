package by.vasilevskiy.dota2analytics.ui.teams.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.vasilevskiy.dota2analytics.data.Team
import by.vasilevskiy.dota2analytics.repositories.TeamsRepository
import kotlinx.coroutines.launch

class TeamsViewModel(private var repository: TeamsRepository) : ViewModel() {

    init {
        repository = TeamsRepository()

        getTeams()
    }

    val result = MutableLiveData<List<Team>>()
    val selected = MutableLiveData<Team>()

    fun select(position: Int) {
        selected.value = result.value?.get(position)
    }

    private fun getTeams() {
        viewModelScope.launch {
            val response = repository.getTeams()
            result.postValue(response)
        }
    }

    /*private fun getSpecificTeam() {
        provideApi().getTeams().enqueue(object : Callback<Team> {
            override fun onFailure(call: Call<Team>, t: Throwable) {

            }

            override fun onResponse(call: Call<Team>, response: Response<Team>) {

            }
        })
    }*/
}
