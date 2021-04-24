package by.vasilevskiy.dota2analytics.ui.news

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NewsViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val context: Context = context

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}