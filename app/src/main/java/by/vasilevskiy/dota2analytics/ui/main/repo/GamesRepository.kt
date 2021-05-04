package by.vasilevskiy.dota2analytics.ui.main.repo

import by.vasilevskiy.dota2analytics.models.UpcomingMatch

interface GamesRepository {
    fun getUpcomingMatches(): List<UpcomingMatch>
}