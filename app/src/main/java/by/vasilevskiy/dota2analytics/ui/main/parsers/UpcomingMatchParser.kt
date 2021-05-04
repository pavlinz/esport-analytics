package by.vasilevskiy.dota2analytics.ui.main.parsers

import by.vasilevskiy.dota2analytics.models.UpcomingMatch
import by.vasilevskiy.dota2analytics.utils.Constants
import org.jsoup.Jsoup
import java.lang.Exception
import javax.inject.Inject

class UpcomingMatchParser @Inject constructor() {

    fun getData(): MutableList<UpcomingMatch> {

        var listUpcomingMatches: MutableList<UpcomingMatch> = mutableListOf()
        try {
            val baseUrl = "https://liquipedia.net"

            val document = Jsoup.connect(Constants.UPCOMING_MATCHES_URL).get()

            val element =
                document.select("table[class=wikitable wikitable-striped infobox_matches_content]")

            var count = 1
            var counter = 0
            var versusCounter = 0

            for (i in 0 until 51) {

                var firstTeamName: String
                var secondTeamName: String

                val leagueName = element.select("td[class=match-filler]")
                    .select("a")
                    .eq(i + count)
                    .text()

                val linkImageLeagueLogo = baseUrl + element.select("td[class=match-filler]")
                    .select("img")
                    .eq(i)
                    .attr("src")

                val firstTeamLogo = baseUrl + element.select("td[class=team-left]")
                    .select("img")
                    .eq(i)
                    .attr("src")

                val secondTeamLogo = baseUrl + element.select("td[class=team-right]")
                    .select("img")
                    .eq(i)
                    .attr("src")

                if (firstTeamLogo == Constants.FORBIDDEN_URL) {
                    firstTeamName = element.select("td[class=team-left]")
                        .select("span[class=team-template-text]")
                        .select("abbr")
                        .eq(i + counter)
                        .text()

                    counter -= 1
                } else {
                    firstTeamName = element.select("td[class=team-left]")
                        .select("a")
                        .eq(i + counter)
                        .text()

                    counter += 1
                }

                if (secondTeamLogo == Constants.FORBIDDEN_URL) {
                    secondTeamName = element.select("td[class=team-right]")
                        .select("span[class=team-template-text]")
                        .select("abbr")
                        .eq(i + count)
                        .text()

                    count -= 1
                } else {
                    secondTeamName = element.select("td[class=team-right]")
                        .select("a")
                        .eq(i + count)
                        .text()

                    count += 1
                }

                val versus = element.select("td[class=versus]")
                    .select("div")
                    .eq(i + versusCounter)
                    .text().toUpperCase()
                versusCounter += 1

                val timer = element.select("td[class=match-filler]")
                    .select("span[class=match-countdown]")
                    .eq(i)
                    .text()

                val time = editTheDate(timer)

                listUpcomingMatches.add(
                    UpcomingMatch(
                        leagueName,
                        linkImageLeagueLogo,
                        firstTeamName,
                        secondTeamName,
                        firstTeamLogo,
                        secondTeamLogo,
                        versus,
                        time
                    )
                )
            }
        } catch (e: Exception) {
        }
        return listUpcomingMatches
    }

    private fun editTheDate(timer: String): String {
        var changedTime = timer.substringBeforeLast(' ')

        val colonIndex = changedTime.indexOf(':')
        var changedTimeBuilder = StringBuilder()
        var count = 1
        var char = changedTime[colonIndex - count++]
        changedTimeBuilder.append(char)

        char = changedTime[colonIndex - count]
        if (changedTime[colonIndex - count] != ' ') {
            changedTimeBuilder.insert(0, char)
        }

        changedTimeBuilder = when (changedTimeBuilder.toString().toInt()) {
            21 -> StringBuilder("00")
            22 -> StringBuilder("01")
            23 -> StringBuilder("02")
            else -> changedTimeBuilder
        }

        val result = " " + ((changedTimeBuilder.toString().toInt() + 3).toString())

        return changedTime.substringBeforeLast(' ') + result + ":" + changedTime.substringAfterLast(
            ':'
        )
    }
}