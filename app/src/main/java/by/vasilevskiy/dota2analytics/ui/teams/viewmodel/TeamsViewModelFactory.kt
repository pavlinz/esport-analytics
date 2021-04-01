package by.vasilevskiy.dota2analytics.ui.teams.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.vasilevskiy.dota2analytics.repositories.TeamsRepository

class TeamsViewModelFactory(private val repository: TeamsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TeamsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}