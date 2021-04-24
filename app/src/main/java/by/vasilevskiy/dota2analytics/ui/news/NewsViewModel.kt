package by.vasilevskiy.dota2analytics.ui.news

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import by.vasilevskiy.dota2analytics.helpers.NetworkManager
import by.vasilevskiy.dota2analytics.R
import by.vasilevskiy.dota2analytics.models.news.NewsItem
import by.vasilevskiy.dota2analytics.ui.news.parsers.EnNewsParser
import by.vasilevskiy.dota2analytics.ui.news.parsers.NewsParser
import by.vasilevskiy.dota2analytics.ui.news.parsers.RuNewsParser
import by.vasilevskiy.dota2analytics.utils.showNoNetworkConnectionToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(var context: Context) : ViewModel() {

    var news = MutableLiveData<MutableList<NewsItem>>()
    var newsItem = MutableLiveData<NewsItem>()
    var newsParser = MutableLiveData<NewsParser>()

    fun selectNewsItem(position: Int) {
        if (NetworkManager.isNetworkAvailable(context)) {
            CoroutineScope(Dispatchers.IO).launch {
                news.value?.get(position)?.let { newsParser.value?.parseNewsPage(it) }
                newsItem.postValue(news.value?.get(position))
            }
        } else {
            context.showNoNetworkConnectionToast()
        }
    }

    fun getNews() = viewModelScope.launch {
        if (NetworkManager.isNetworkAvailable(context)) {
            CoroutineScope(Dispatchers.IO).launch {
                val list = newsParser.value?.parseNews()
                news.postValue(list)
            }
        } else {
            context.showNoNetworkConnectionToast()
        }
    }

    fun checkNewsParserType() {
        if (NetworkManager.isNetworkAvailable(context)) {
            val res = context.resources
            val languageValues = res.getStringArray(R.array.language_values)

            when (PreferenceManager.getDefaultSharedPreferences(context)
                .getString(res.getString(R.string.language_key), languageValues[0])) {
                languageValues[0] -> {
                    newsParser.value = EnNewsParser()
                }
                languageValues[1] -> {
                    newsParser.value = RuNewsParser()
                }
            }
        } else {
            context.showNoNetworkConnectionToast()
        }
    }
}