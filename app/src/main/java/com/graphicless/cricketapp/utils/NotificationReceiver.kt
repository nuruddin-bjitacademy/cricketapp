package com.graphicless.cricketapp.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.database.LocalDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Get the match ID from the intent
        val matchId = intent.getIntExtra("match_id", 0)


        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            // Get the match from the Room database
            val match =
                LocalDatabase.instance(MyApplication.instance).cricketDao().getFixtureById(matchId)

            // Create a notification channel for Android Oreo and higher
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelId = "match_notification"
                val channelName = "Match Notification"
                val channelDescription = "Notification for upcoming matches"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(channelId, channelName, importance)
                channel.description = channelDescription

                val notificationManager = context.getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(channel)
            }

            // Create the notification content
            val title = "Upcoming Match"
            val message =
                "The match between ${match.team1} and ${match.team2} is starting in 15 minutes."
            val notificationBuilder = NotificationCompat.Builder(context, "match_notification")
                .setSmallIcon(R.drawable.icon_ball)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            // Create a unique ID for the notification
            val notificationId = matchId

            // Show the notification
            with(NotificationManagerCompat.from(context)) {
                if (ActivityCompat.checkSelfPermission(
                        MyApplication.instance,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return@launch
                }
                notify(notificationId, notificationBuilder.build())
            }
        }
    }
}
