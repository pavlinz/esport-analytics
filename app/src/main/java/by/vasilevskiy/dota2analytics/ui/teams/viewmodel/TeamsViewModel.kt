package by.vasilevskiy.dota2analytics.ui.teams.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.vasilevskiy.dota2analytics.data.Match
import by.vasilevskiy.dota2analytics.data.Player
import by.vasilevskiy.dota2analytics.data.Team
import by.vasilevskiy.dota2analytics.data.profile.PlayerProfile
import by.vasilevskiy.dota2analytics.repositories.TeamsRepository
import kotlinx.coroutines.*

class TeamsViewModel(private var repository: TeamsRepository) : ViewModel() {

    var matches = MutableLiveData<List<Match>>()
    var players = MutableLiveData<List<Player>>()
    var result = MutableLiveData<List<Team>>()
    var selected = MutableLiveData<Team>()

    init {
        getTeams()
    }

    fun select(position: Int) {
        selected.value = result.value?.get(position)
    }

    private fun getTeams() {
        viewModelScope.launch {
            val response = repository.getTeams()
            result.value = response
        }
    }

    fun getTeamPlayers() {
        viewModelScope.launch {
            val result = repository.getTeamPlayers(selected.value!!.team_id)
            (result as MutableList).removeAll { it.is_current_team_member == false || it.is_current_team_member == null }
            players.postValue(result)
        }
    }

    fun getTeamMatches() {
        viewModelScope.launch {
            val result = repository.getTeamMatches(selected.value!!.team_id)
            for (i in result.size-1 downTo 20) (result as MutableList).removeAt(i)
            matches.postValue(result)
        }
    }
}
