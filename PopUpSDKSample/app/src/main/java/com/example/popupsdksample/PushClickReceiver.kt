package com.example.popupsdksample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class PushClickReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("FCM", "✅ PushClickReceiver received push click!")

        val messageId = intent?.getStringExtra("message_id")
        val clientExternalId = intent?.getStringExtra("external_client_id")

        Log.d("FCM", "✅ PushClickReceiver: messageId=$messageId, clientExternalId=$clientExternalId")

        if (!messageId.isNullOrEmpty() && !clientExternalId.isNullOrEmpty()) {
            val openIntent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                putExtra("message_id", messageId)
                putExtra("client_external_id", clientExternalId)
            }

            context?.startActivity(openIntent)
        }
    }
}