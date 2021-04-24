package by.vasilevskiy.dota2analytics.ui.news.parsers

import android.util.Log
import by.vasilevskiy.dota2analytics.models.news.NewsItem
import by.vasilevskiy.dota2analytics.utils.Constants
import org.jsoup.Jsoup

class RuNewsParser : NewsParser {

    override fun parseNews(): MutableList<NewsItem> {
        val news = mutableListOf<NewsItem>()

        val document = Jsoup.connect(Constants.RU_NEWS_URL).get()
        val element = document.select("div[class=list-sorted-news__item ]")

        var count = 0

        for (i in 0 until 10) {
            val newsImageUrl = "https://dota2.net" +
                element.select("div[class=news-item news-item_list-news news-item_other-news ]")
                    .select("a")
                    .eq(i + count++)
                    .attr("style")
                    .substringAfter("'").substringBefore("'")

            val newsTitle = element.select("div[class=news-item__text]")
                .select("a")
                .eq(i)
                .text()

            val fullNewsUrl = element.select("div[class=list-sorted-news__item ]")
                .select("div[class=news-item news-item_list-news news-item_other-news ]")
                .select("a")
                .eq(i + count)
                .attr("href")

            val date = element.select("div[class=news-item__text]")
                .select("div[class=news-item__statistics]")
                .eq(i)
                .text()

            news.add(NewsItem(newsImageUrl, newsTitle, date, fullNewsUrl))
            Log.d("Test", "${news[i]}")
        }
        return news
    }

    override fun parseNewsPage(newsItem: NewsItem) {
        val document = Jsoup.connect(newsItem.fullNewsUrl).get()
        val element = document.select("div[class=post-content]")

        val articleText = element.html()

        newsItem.text = articleText
    }
}