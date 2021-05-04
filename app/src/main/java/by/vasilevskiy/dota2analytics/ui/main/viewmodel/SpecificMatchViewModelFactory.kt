package by.vasilevskiy.dota2analytics.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.vasilevskiy.dota2analytics.ui.main.repo.GamesRepoImpl

class SpecificMatchViewModelFactory(var repo: GamesRepoImpl) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                repo,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}