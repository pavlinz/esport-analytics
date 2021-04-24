package by.vasilevskiy.dota2analytics.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.vasilevskiy.dota2analytics.helpers.NetworkManager
import by.vasilevskiy.dota2analytics.models.UpcomingMatch
import by.vasilevskiy.dota2analytics.ui.main.parsers.UpcomingMatchParser
import by.vasilevskiy.dota2analytics.utils.removeForbiddenCharacters
import by.vasilevskiy.dota2analytics.utils.showNoNetworkConnectionToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(var context: Context) : ViewModel() {

    var listUpcomingMatches: MutableList<UpcomingMatch> = mutableListOf()
    var selectedMatch = MutableLiveData<UpcomingMatch>()

    fun getRefactoredName() =
        (selectedMatch.value?.firstTeamName + selectedMatch.value?.secondTeamName).removeForbiddenCharacters()

    fun selectUpcomingMatch(position: Int) {
        selectedMatch.value = listUpcomingMatches[position]
    }

    fun getData() {
        if (NetworkManager.isNetworkAvailable(context)) {
            listUpcomingMatches = UpcomingMatchParser().getData()
        } else {
            CoroutineScope(Dispatchers.Main).launch {
                context.showNoNetworkConnectionToast()
            }
        }
    }
}