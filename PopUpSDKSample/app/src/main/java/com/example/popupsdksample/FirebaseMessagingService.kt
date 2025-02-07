package com.example.popupsdksample

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import loymax.smartcom.sdk.apis.CommunicationApi
import loymax.smartcom.sdk.infrastructure.ApiClient
import loymax.smartcom.sdk.models.LogPushEventRequest
import loymax.smartcom.sdk.models.LogPushEventRequestDataInner

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d("FCM", "Message received from: ${remoteMessage.from}")
        Log.d("FCM", "Message remoteMessage: ${remoteMessage.toString()}")
        Log.d("FCM", "Message data: ${remoteMessage.data}")

        val messageId = remoteMessage.data["message_id"]
        val clientExternalId = remoteMessage.data["client_external_id"]
        val title = remoteMessage.data["title"] ?: remoteMessage.notification?.title ?: "New Message"
        val body = remoteMessage.data["body"] ?: remoteMessage.notification?.body ?: ""

        Log.d("FCM", "messageId: $messageId")
        Log.d("FCM", "clientExternalId: $clientExternalId")

        if (!messageId.isNullOrEmpty() && !clientExternalId.isNullOrEmpty()) {
            Log.d("FCM", "✅ sendPushEvent(\"Delivered\", $messageId, $clientExternalId) is being called!")
            sendPushEvent("Delivered", messageId, clientExternalId)
        } else {
            Log.e("FCM", "❌ Missing required data fields (message_id, client_external_id)")
        }

        createNotificationChannel()
        showNotification(title, body, messageId ?: "unknown", clientExternalId ?: "unknown")
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "New token: $token")
        TokenManager.saveFirebaseToken(token)
    }

    fun getFireBaseToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    TokenManager.saveFirebaseToken(token)
                    Log.d("FCM", "Token: $token")
                } else {
                    Log.e("FCM", "Failed to get token", task.exception)
                }
            }
    }

    private fun sendPushEvent(type: String, messageId: String, clientExternalId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val token = TokenManager.getToken() ?: ""
                if (token.isEmpty()) {
                    Log.e("FCM", "❌ TokenManager.getToken() returned null or empty!")
                    return@launch
                }

                val apiClient = ApiClient(
                    baseUrl = TokenManager.getBaseUrl(),
                    bearerToken = token
                )

                val communicationApi = apiClient.createService(CommunicationApi::class.java)

                val requestBody = LogPushEventRequest(
                    data = listOf(
                        LogPushEventRequestDataInner(
                            type = type,
                            messageId = messageId,
                            externalClientId = clientExternalId
                        )
                    )
                )

                Log.d("FCM", "🚀 Sending push event: $type for messageId=$messageId, clientExternalId=$clientExternalId")

                val response = communicationApi.logPushEvent(type, requestBody).execute()

                if (response.isSuccessful) {
                    Log.d("FCM", "✅ Push event sent successfully: $type for $messageId")
                } else {
                    Log.e("FCM", "❌ Failed to send push event: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("FCM", "❌ Error sending push event: ${e.message}")
            }
        }
    }

    private fun showNotification(title: String, message: String, messageId: String, clientExternalId: String) {
        val notificationManager = NotificationManagerCompat.from(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Log.e("FCM", "Notification permission not granted!")
            return
        }

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("message_id", messageId)
            putExtra("client_external_id", clientExternalId)
        }

        Log.d("FCM", "✅ showNotification() → Intent data: messageId=$messageId, clientExternalId=$clientExternalId")

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, "default_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "default_channel",
                "Default Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Used for general notifications"
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)

            Log.d("FCM", "Notification channel created: default_channel")
        }
    }
}