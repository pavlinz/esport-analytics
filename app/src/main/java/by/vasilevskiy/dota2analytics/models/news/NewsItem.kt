package by.vasilevskiy.dota2analytics.models.news

class NewsItem(val imageUrl: String, val title: String, val date: String, val fullNewsUrl: String, var text: String = "") {
    override fun toString(): String {
        return "NewsItem(imageUrl='$imageUrl', title='$title', date='$date', fullNewsUrl='$fullNewsUrl')"
    }
}