package by.vasilevskiy.dota2analytics.helpers

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectionLiveData @Inject constructor(context: Context) : LiveData<Boolean>() {

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    private val validNetworks: MutableSet<Network> = HashSet()

    private fun checkValidNetworks() {
        postValue(validNetworks.size > 0)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onActive() {
        networkCallback = createNetworkCallback()
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NET_CAPABILITY_INTERNET)
            .build()
        cm.registerNetworkCallback(networkRequest, networkCallback)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onInactive() {
        cm.unregisterNetworkCallback(networkCallback)
    }

    private fun createNetworkCallback() =
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP) object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                val networkCapabilities = cm.getNetworkCapabilities(network)
                val hasInternetCapability =
                    networkCapabilities?.hasCapability(NET_CAPABILITY_INTERNET)
                if (hasInternetCapability == true) {
                    validNetworks.add(network)
                }
                checkValidNetworks()
            }

            override fun onLost(network: Network) {
                validNetworks.remove(network)
                checkValidNetworks()
            }
        }
}