package by.vasilevskiy.dota2analytics.helpers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import by.vasilevskiy.dota2analytics.R
import by.vasilevskiy.dota2analytics.utils.Constants

class ReminderBroadcast : BroadcastReceiver() {

    private val notificationId = 101

    override fun onReceive(p0: Context?, p1: Intent?) {
        val builder = NotificationCompat.Builder(p0!!, Constants.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_analytics)
            .setContentTitle("The match begins")
            .setContentText("Going live soon!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(p0)){
            notify(notificationId, builder.build())
        }
    }
}