package by.vasilevskiy.dota2analytics.helpers

import android.content.Context
import android.net.ConnectivityManager

class NetworkManager {

    companion object {
        fun isNetworkAvailable(context: Context) : Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }

}