package by.vasilevskiy.dota2analytics.ui.main.repo

import by.vasilevskiy.dota2analytics.ui.main.parsers.UpcomingMatchParser
import javax.inject.Inject

class GamesRepoImpl @Inject constructor(private val matchParser: UpcomingMatchParser) : GamesRepository {

    override fun getUpcomingMatches() = matchParser.getData()
}