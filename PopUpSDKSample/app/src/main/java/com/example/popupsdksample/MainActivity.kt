package com.example.popupsdksample

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.popupsdksample.ui.smc.SmcFragment
import com.example.popupsdksample.ui.main.MainFragment
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import loymax.smartcom.sdk.apis.SMCClient
import loymax.smartcom.sdk.apis.SMCClientFactory
import loymax.smartcom.sdk.models.PushEvent
import loymax.smartcom.sdk.models.PushEventRequest

class MainActivity : AppCompatActivity() {

    private val requestNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            FirebaseMessagingService().getFireBaseToken()
        } else {
            Toast.makeText(this, "Не включены пуш уведомления", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        FirebaseApp.initializeApp(this)
        Log.d("FirebaseInit", "FirebaseApp initialized: ${FirebaseApp.getApps(this).isNotEmpty()}")
        setContentView(R.layout.activity_main)
        val buttonPop = findViewById<Button>(R.id.pop_up_screen)
        val buttonCms = findViewById<Button>(R.id.cms_screen)
        buttonPop.setOnClickListener {
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
            }
        }

        buttonCms.setOnClickListener {
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SmcFragment.newInstance())
                    .commitNow()
            }
        }

        Log.d("FCM", "✅ MainActivity.onCreate() called!")

        handlePushIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("FCM", "✅ MainActivity.onNewIntent() called!")
        handlePushIntent(intent)
    }

    private fun handlePushIntent(intent: Intent?) {
        val messageId = intent?.getStringExtra("message_id")
        val clientExternalId = intent?.getStringExtra("client_external_id")

        Log.d("FCM", "✅ Intent extras received in MainActivity: messageId=$messageId, clientExternalId=$clientExternalId")

        if (!messageId.isNullOrEmpty() && !clientExternalId.isNullOrEmpty()) {
            Log.d("FCM", "✅ Push clicked: Sending Read event for messageId=$messageId")
            sendPushEvent("Read", messageId, clientExternalId)
        } else {
            Log.e("FCM", "❌ Missing message_id or client_external_id in MainActivity!")
        }
    }

    private fun sendPushEvent(type: String, messageId: String, externalClientId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val token = TokenManager.getToken() ?: return@launch
                val requestBody = PushEventRequest(
                    data = listOf(
                        PushEvent(
                            type = type,
                            messageId = messageId,
                            externalClientId = externalClientId
                        )
                    )
                )

                val response = SMCClientFactory.get().service.sendPushEvent(token, requestBody).execute()

                if (response.isSuccessful) {
                    Log.d("FCM", "Push event sent: $type for $messageId")
                } else {
                    Log.e("FCM", "Failed to send push event: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("FCM", "Error sending push event: ${e.message}")
            }
        }
    }
}