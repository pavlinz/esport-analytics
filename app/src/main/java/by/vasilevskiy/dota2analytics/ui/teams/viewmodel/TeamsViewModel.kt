package by.vasilevskiy.dota2analytics.ui.teams.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.vasilevskiy.dota2analytics.helpers.NetworkManager
import by.vasilevskiy.dota2analytics.models.Match
import by.vasilevskiy.dota2analytics.models.Player
import by.vasilevskiy.dota2analytics.models.Team
import by.vasilevskiy.dota2analytics.data.DefaultTeamsRepository
import by.vasilevskiy.dota2analytics.utils.showNoNetworkConnectionToast
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class TeamsViewModel @Inject constructor(
    private var repository: DefaultTeamsRepository,
    @ApplicationContext var context: Context,
) : ViewModel() {

    private val TAG = "TeamsViewModel"

    var matches = MutableLiveData<List<Match>>()
    var players = MutableLiveData<List<Player>>()
    var result = MutableLiveData<List<Team>>()
    var selected = MutableLiveData<Team>()

    var searchList = mutableListOf<Team>()

    init {
        getTeams()
    }

    fun select(position: Int) {
        selected.value = searchList[position]
    }

    fun getSelectedTeam(): Array<String> {
        selected.value.also { team ->
            if (team != null) {
                return arrayOf(team.wins.toString(), team.losses.toString(), team.rating.toString())
            }
        }
        return arrayOf()
    }

    private fun getTeams() {
        if (NetworkManager.isNetworkAvailable(context)) {
            viewModelScope.launch {
                try {
                    val response = repository.getTeams()
                    result.postValue(response)
                } catch (e: Exception) {
                    Log.d(TAG, "getTeams: ${e.message.toString()}")
                }
            }
        } else {
            context.showNoNetworkConnectionToast()
        }
    }

    fun getTeamPlayers() {
        if (NetworkManager.isNetworkAvailable(context)) {
            viewModelScope.launch {
                val result = repository.getTeamPlayers(selected.value!!.team_id)
                (result as MutableList).removeAll { it.is_current_team_member == false || it.is_current_team_member == null }
                players.postValue(result)
            }
        } else {
            context.showNoNetworkConnectionToast()
        }
    }

    fun getTeamMatches() {
        if (NetworkManager.isNetworkAvailable(context)) {
            viewModelScope.launch {
                try {
                    val result = repository.getTeamMatches(selected.value!!.team_id)
                    for (i in result.size - 1 downTo 20) (result as MutableList).removeAt(i)
                    matches.postValue(result)
                } catch (e: Exception) {
                    Log.d(TAG, "getTeamMatches: ${e.message.toString()}")
                }
            }
        } else {
            context.showNoNetworkConnectionToast()
        }
    }
}
