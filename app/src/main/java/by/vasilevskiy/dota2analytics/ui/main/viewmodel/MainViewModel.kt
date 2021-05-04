package by.vasilevskiy.dota2analytics.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.vasilevskiy.dota2analytics.models.UpcomingMatch
import by.vasilevskiy.dota2analytics.ui.main.repo.GamesRepoImpl
import by.vasilevskiy.dota2analytics.utils.removeForbiddenCharacters

class MainViewModel(
    private val repo: GamesRepoImpl,
) : ViewModel() {

    var listUpcomingMatches: MutableList<UpcomingMatch> = mutableListOf()
    var selectedMatch = MutableLiveData<UpcomingMatch>()

    fun getRefactoredName() =
        (selectedMatch.value?.firstTeamName + selectedMatch.value?.secondTeamName).removeForbiddenCharacters()

    fun selectUpcomingMatch(position: Int) {
        selectedMatch.value = listUpcomingMatches[position]
    }

    fun getData() {
        listUpcomingMatches = repo.getUpcomingMatches()
    }
}