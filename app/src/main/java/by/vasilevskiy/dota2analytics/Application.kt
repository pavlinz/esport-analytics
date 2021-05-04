package by.vasilevskiy.dota2analytics

import android.app.Application
import by.vasilevskiy.dota2analytics.ui.teams.repo.DefaultTeamsRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application() {
    val repository by lazy { DefaultTeamsRepository() }
}