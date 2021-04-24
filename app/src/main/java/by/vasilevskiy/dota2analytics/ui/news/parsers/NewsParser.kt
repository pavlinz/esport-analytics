package by.vasilevskiy.dota2analytics.ui.news.parsers

import by.vasilevskiy.dota2analytics.models.news.NewsItem

interface NewsParser {

    fun parseNews(): MutableList<NewsItem>
    fun parseNewsPage(newsItem: NewsItem)

}