package by.vasilevskiy.dota2analytics.ui.teams.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.vasilevskiy.dota2analytics.ui.teams.repo.DefaultTeamsRepository

class TeamsViewModelFactory constructor(
    private val repository: DefaultTeamsRepository,
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TeamsViewModel(repository, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}