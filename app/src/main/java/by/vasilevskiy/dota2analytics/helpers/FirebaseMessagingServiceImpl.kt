package by.vasilevskiy.dota2analytics.helpers

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class FirebaseMessagingServiceImpl : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("Test", "Refreshed token: " + token)
        //sendRegistrationToServer(token)
    }

}