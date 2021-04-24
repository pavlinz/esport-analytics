package by.vasilevskiy.dota2analytics.ui.news.parsers

import android.util.Log
import by.vasilevskiy.dota2analytics.models.news.NewsItem
import by.vasilevskiy.dota2analytics.utils.Constants.Companion.EN_NEWS_URL
import org.jsoup.Jsoup

class EnNewsParser : NewsParser {

    override fun parseNews(): MutableList<NewsItem> {
        val news = mutableListOf<NewsItem>()

        val document = Jsoup.connect(EN_NEWS_URL).get()
        val element = document.select("article")

        for (i in 0 until 10) {
            val newsImageUrl = element
                .select("img")
                .eq(i)
                .attr("src")

            val newsTitle = element.select("div[class=right]")
                .select("h3[class=entry-title]")
                .select("a")
                .eq(i)
                .text()

            val fullNewsUrl = element.select("div[class=right]")
                .select("h3[class=entry-title]")
                .select("a")
                .eq(i)
                .attr("href")

            val date = element.select("div[class=right]")
                .select("time[class=entry-date published]")
                .eq(i)
                .text()

            news.add(NewsItem(newsImageUrl, newsTitle, date, fullNewsUrl))
            Log.d("Test", "${news[i]}")
        }
        return news
    }

    override fun parseNewsPage(newsItem: NewsItem) {
        val document = Jsoup.connect(newsItem.fullNewsUrl).get()
        val element = document.select("article")

        document.select("table").remove()

        val articleText = element.select("div[class=entry-content]")
            .html()

        newsItem.text = articleText
    }

}