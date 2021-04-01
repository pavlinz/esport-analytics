package by.vasilevskiy.dota2analytics

import android.app.Application
import by.vasilevskiy.dota2analytics.repositories.TeamsRepository

class Application : Application() {
    val repository by lazy { TeamsRepository() }
}