package com.rrbofficial.androiduipracticekotlin

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.maps.GoogleMap
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.onesignal.OneSignal
import com.onesignal.debug.LogLevel
import com.rrbofficial.androiduipracticekotlin.GoogleMaps.GoogleMaps
import com.rrbofficial.androiduipracticekotlin.GoogleMaps.TypeAndStyle
import com.rrbofficial.androiduipracticekotlin.Notifications.util.AppConstant
import com.rrbofficial.androiduipracticekotlin.Notifications.util.AppConstant.NOTIFICATION_ACTION_CHANNEL_ID
import com.rrbofficial.androiduipracticekotlin.Notifications.util.AppConstant.NOTIFICATION_BIG_PICTURE_STYLE_CHANNEL_ID
import com.rrbofficial.androiduipracticekotlin.Notifications.util.AppConstant.NOTIFICATION_BIG_TEXT_STYLE_CHANNEL_ID
import com.rrbofficial.androiduipracticekotlin.Notifications.util.AppConstant.NOTIFICATION_CONTENT_INTENT_CHANNEL_ID
import com.rrbofficial.androiduipracticekotlin.Notifications.util.AppConstant.NOTIFICATION_CUSTOM_SOUND_CHANNEL_ID
import com.rrbofficial.androiduipracticekotlin.Notifications.util.AppConstant.NOTIFICATION_CUSTOM_STYLE_CHANNEL_ID
import com.rrbofficial.androiduipracticekotlin.Notifications.util.AppConstant.NOTIFICATION_DOWNLOAD_STYLE_CHANNEL_ID
import com.rrbofficial.androiduipracticekotlin.Notifications.util.AppConstant.NOTIFICATION_HIGH_CHANNEL_ID
import com.rrbofficial.androiduipracticekotlin.Notifications.util.AppConstant.NOTIFICATION_INBOX_STYLE_CHANNEL_ID
import com.rrbofficial.androiduipracticekotlin.Notifications.util.AppConstant.NOTIFICATION_LOW_CHANNEL_ID
import com.rrbofficial.androiduipracticekotlin.Notifications.util.AppConstant.NOTIFICATION_MEDIA_STYLE_CHANNEL_ID
import com.rrbofficial.androiduipracticekotlin.Notifications.util.AppConstant.NOTIFICATION_MESSAGING_STYLE_CHANNEL_ID
import com.rrbofficial.androiduipracticekotlin.Notifications.util.AppConstant.NOTIFICATION_ONGOING_CHANNEL_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Locale

class MyApplication : Application() {
    private lateinit var crashlytics: FirebaseCrashlytics
    val ONESIGNAL_APP_ID = "e066ae09-b166-455f-a3a4-8b21edca0da9"
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
    // Timber Logcat
        Timber.plant(Timber.DebugTree())

        // one signal notification
        // Verbose Logging set to help debug issues, remove before releasing your app.
        OneSignal.Debug.logLevel = LogLevel.VERBOSE

        // OneSignal Initialization
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID)

        // requestPermission will show the native Android notification permission prompt.
        // NOTE: It's recommended to use a OneSignal In-App Message to prompt instead.
        CoroutineScope(Dispatchers.IO).launch {
            OneSignal.Notifications.requestPermission(false)
        }

        //set locale
        setAppLocale(Locale.getDefault())

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Enable Crashlytics collection
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)

        // Call default Notification channel
        createNotificationChannel()

        // Apply theme based on shared preferences
        applyTheme()

    }



    private fun setAppLocale(locale: Locale) {
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                .build()

            val defaultNotification = NotificationChannel(
                AppConstant.NOTIFICATION_DEFAULT_CHANNEL_ID,
                "Default Notification Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "This is the default notification channel"
                lightColor = Color.parseColor("#EE82EE")
                enableVibration(true)
                vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            }

            // High Notification Channel
            val highNotification = NotificationChannel(
                NOTIFICATION_HIGH_CHANNEL_ID,
                "High Notification Channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "This is the HIGH notification channel"
                lightColor = Color.GREEN
            }

            // Low Notification Channel
            val lowNotification = NotificationChannel(
                NOTIFICATION_LOW_CHANNEL_ID,
                "Low Notification Channel",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "This is the low notification channel"
                lightColor = Color.GREEN
            }
            // Action Notification Channel
            val actionNotification = NotificationChannel(
                NOTIFICATION_ACTION_CHANNEL_ID,
                "Action Notification Channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "This is the action notification channel"
                lightColor = Color.GREEN
            }

            // Content Intent Notification Channel
            val contentIntentNotification = NotificationChannel(
                NOTIFICATION_CONTENT_INTENT_CHANNEL_ID,
                "Content Intent Notification Channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "This is the content intent notification channel"
                lightColor = Color.RED
            }

            // Ongoing Notification Channel
            val onGoingNotification = NotificationChannel(
                NOTIFICATION_ONGOING_CHANNEL_ID,
                "OnGoing Notification Channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "This is the on going notification channel"
                lightColor = Color.RED
            }

            // Custom Sound Notification Channel
            val customSoundNotification = NotificationChannel(
                NOTIFICATION_CUSTOM_SOUND_CHANNEL_ID,
                "custom sound Notification Channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "This is custom sound notification channel"
                lightColor = Color.RED
                setSound(getUriFromResourceFile(this@MyApplication, R.raw.arabianmusicnotification), audioAttributes)
            }

            // Big Text Style Notification Channel
            val bigTextStyleNotification = NotificationChannel(
                NOTIFICATION_BIG_TEXT_STYLE_CHANNEL_ID,
                "big text style Notification Channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "This is big text style notification channel"
                lightColor = Color.RED
                setSound(getUriFromResourceFile(this@MyApplication, R.raw.arabianmusicnotification), audioAttributes)
            }

            // Inbox Style Notification Channel
            val inboxStyleNotification = NotificationChannel(
                NOTIFICATION_INBOX_STYLE_CHANNEL_ID,
                "inbox style Notification Channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "inbox style notification channel"
                lightColor = Color.RED
                setSound(getUriFromResourceFile(this@MyApplication, R.raw.notificationdoorbell), audioAttributes)
            }

            // Big Picture Style Notification Channel
            val bigPictureStyleNotification = NotificationChannel(
                NOTIFICATION_BIG_PICTURE_STYLE_CHANNEL_ID,
                "Big picture style Notification Channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "big picture style notification channel"
                lightColor = Color.RED
                setSound(getUriFromResourceFile(this@MyApplication, R.raw.notifictionhappybell), audioAttributes)
            }


            // Download Style Notification Channel
            val downloadStyleNotification = NotificationChannel(
                NOTIFICATION_DOWNLOAD_STYLE_CHANNEL_ID,
                "download style Notification Channel",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "download style notification channel"
                lightColor = Color.RED
            }


            // Messaging Style Notification Channel
            val messagingStyleNotification = NotificationChannel(
                NOTIFICATION_MESSAGING_STYLE_CHANNEL_ID,
                "messaging style Notification Channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "messaging style notification channel"
                lightColor = Color.RED
            }

            // Media Style Notification Channel
            val mediaStyleNotification = NotificationChannel(
                NOTIFICATION_MEDIA_STYLE_CHANNEL_ID,
                "media style Notification Channel",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "media style notification channel"
                lightColor = Color.RED
            }

            // Custom Style Notification Channel
            val customStyleNotification = NotificationChannel(
                NOTIFICATION_CUSTOM_STYLE_CHANNEL_ID,
                "custom style Notification Channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "custom style notification channel"
                lightColor = Color.RED
            }


            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannels(
                listOf(defaultNotification,
                    highNotification,
                    lowNotification,
                    actionNotification,
                    contentIntentNotification,
                    onGoingNotification,
                    customSoundNotification,
                    bigTextStyleNotification,
                    inboxStyleNotification,
                    bigPictureStyleNotification,
                    downloadStyleNotification,
                    messagingStyleNotification,
                    mediaStyleNotification,
                    customStyleNotification
                    )
            )
        }
    }

    private fun applyTheme() {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        val isNightMode = sharedPreferences.getBoolean("NightMode", false)
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun getUriFromResourceFile(context: Context, resourceId: Int): Uri {
        return Uri.parse("android.resource://$packageName/$resourceId")
    }
}
