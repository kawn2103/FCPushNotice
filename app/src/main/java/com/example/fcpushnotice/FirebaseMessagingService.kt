package com.example.fcpushnotice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.fcpushnotice.NotificationType.*
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingService: FirebaseMessagingService(){
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        createNotificationChannel()

        val type = remoteMessage.data["type"]
            ?.let { valueOf(it) }
        val title = remoteMessage.data["title"]
        val message = remoteMessage.data["message"]

        type ?: return



        NotificationManagerCompat.from(this)
            .notify(type.id,createNotification(type,title,message))
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = CHANNEL_DESCRIPTION

            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }
    }

    private fun createNotification (
        type: NotificationType,
        title: String?,
        message: String?
    ): Notification{
        val intent = Intent(this,MainActivity::class.java).apply {
            putExtra("notificationType", "${type.title} 타입")
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(this,type.id,intent,FLAG_UPDATE_CURRENT)

        val notificationBuilder =  NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.notifications_icon)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        when(type){
            NORMAL -> Unit
            EXPANDABLE -> {
                notificationBuilder.setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(
                            "😍🥲😭😘🥲😘🥲😘😭😍🥲😘😍😍😂😍😍😭😍😍😘😘🥰" +
                                    "😍😍👍😂👍☺️😂😒😒😂👍😍😭😍🥲😘🥰☺️😭😂👍" +
                                    "😭😍❤️😍👌😊😒😊👌☺️😔😊😏😩😁😁😏😏😳😒😉" +
                                    "😳😊😔😊😏😁"
                        )
                )
            }
            CUSTOM -> {
                notificationBuilder.setStyle(
                    NotificationCompat.DecoratedCustomViewStyle()
                )
                    .setCustomContentView(
                        RemoteViews(
                            packageName,
                            R.layout.view_custom_notificatin
                        ).apply {
                            setTextViewText(R.id.title,title)
                            setTextViewText(R.id.message,message)
                        }
                    )

            }
        }

        return notificationBuilder.build()
    }

    companion object{
        private const val CHANNEL_NAME = "Emoji party"
        private const val CHANNEL_DESCRIPTION = "Emoji Party를 위한 채널"
        private const val CHANNEL_ID = "Channel Id"
    }
}