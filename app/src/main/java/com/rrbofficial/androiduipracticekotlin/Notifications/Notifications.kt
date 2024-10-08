package com.rrbofficial.androiduipracticekotlin.Notifications

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.rrbofficial.androiduipracticekotlin.MainActivity
import com.rrbofficial.androiduipracticekotlin.Notifications.util.AppConstant
import com.rrbofficial.androiduipracticekotlin.Notifications.util.NotificationHelper
import com.rrbofficial.androiduipracticekotlin.Notifications.util.NotificationHelper.openNotificationSetting
import com.rrbofficial.androiduipracticekotlin.R
import com.rrbofficial.androiduipracticekotlin.databinding.ActivityNotificationsBinding


class Notifications : AppCompatActivity() {

    private var _binding: ActivityNotificationsBinding? = null


    // binding to binding ---------------------------------------------
   private  val binding :ActivityNotificationsBinding get() = _binding!!

    private var bitmap : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // set-up glide
        setUpGlide()


        _binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonClickListener()
    }
    private fun buttonClickListener()
    {

        // disable button if notification is blocked
//        binding.btndefaultNotification.isEnabled =  NotificationHelper.isNotificationEnabled(this)
//        binding.btnHighNotificaiton.isEnabled =  NotificationHelper.isNotificationEnabled(this)


        if(!NotificationHelper.isNotificationEnabled(this))
        {
          showNotificationSettingsDialog(this)
        }


        // default notification
    binding.btndefaultNotification.setOnClickListener()
    {
        val title = binding.txtTitle.text.toString()
        val msg = binding.txtMessage.text.toString()
        NotificationHelper.defaultNotification(this,title,msg)
    }
        // high notification
        binding.btnHighNotificaiton.setOnClickListener()
        {

            // checking Notification channel status
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                if(!NotificationHelper.isNotificationChannelEnabled(this, AppConstant.NOTIFICATION_HIGH_CHANNEL_ID))
                {
                    val title = binding.txtTitle.text.toString()
                    val msg = binding.txtMessage.text.toString()
                    NotificationHelper.highNotification(this,title,msg)
                }
                else
                {
                    NotificationHelper.openNotificationChannelSetting(this, AppConstant.NOTIFICATION_HIGH_CHANNEL_ID)
                    Toast.makeText(this, "Please enable notification channel", Toast.LENGTH_SHORT).show()
                }
            }
            else
            {
                val title = binding.txtTitle.text.toString()
                val msg = binding.txtMessage.text.toString()
                NotificationHelper.highNotification(this,title,msg)
            }
        }

        // low notification
        binding.btnLowNotificaiton.setOnClickListener()
        {
            val title = binding.txtTitle.text.toString()
            val msg = binding.txtMessage.text.toString()
            NotificationHelper.lowNotification(this,title,msg)
        }

        // action notification
        binding.btnActionNotificaiton.setOnClickListener()
        {
            val title = binding.txtTitle.text.toString()
            val msg = binding.txtMessage.text.toString()
            NotificationHelper.actionNotification(this,title,msg)
        }

        // content  intent notification
        binding.btnContentIntenNotificaiton.setOnClickListener()
        {
            val title = binding.txtTitle.text.toString()
            val msg = binding.txtMessage.text.toString()
            NotificationHelper.contentIntentNotification(this,title,msg)
        }

        // onGoing intent Notification
        binding.btnOnGoingNotificaiton.setOnClickListener()
        {
            val title = binding.txtTitle.text.toString()
            val msg = binding.txtMessage.text.toString()
            NotificationHelper.onGoingNotification(this,title,msg)
        }


        // custom sound Notification
        binding.btncustomSoundNotificaiton.setOnClickListener()
        {
            val title = binding.txtTitle.text.toString()
            val msg = binding.txtMessage.text.toString()
            NotificationHelper.customSoundNotification(this,title,msg)
        }


        // big text style Notification
        binding.btnbigTextStyleNotificaiton.setOnClickListener()
        {
            val title = binding.txtTitle.text.toString()
            val msg = binding.txtMessage.text.toString()
            NotificationHelper.bigTextStyleNotification(this,title,msg)
        }

        // inbox style Notification
        binding.btninboxStyleNotificaiton.setOnClickListener()
        {
            val title = binding.txtTitle.text.toString()
            val msg = binding.txtMessage.text.toString()
            NotificationHelper.inboxStyleNotification(this,title,msg)
        }


        // big picture style Notification
        binding.btnbigPictureStyleNotificaiton.setOnClickListener()
        {
            val title = binding.txtTitle.text.toString()
            val msg = binding.txtMessage.text.toString()
            // check bitmap not null then pass it
            NotificationHelper.bigPictureStyleNotification(this,title,msg,bitmap!!)
        }


        // download style Notification
        binding.btndownloadStyleNotificaiton.setOnClickListener()
        {
            val title = binding.txtTitle.text.toString()
            val msg = binding.txtMessage.text.toString()
            NotificationHelper.downloadStyleNotification(this,title,msg)
        }

        // Messaging style Notification
        binding.btnmessagingStyleNotificaiton.setOnClickListener()
        {
            NotificationHelper.messagingStyleNotification(this)
        }

        // Media style Notification
        binding.btnmediaStyleNotificaiton.setOnClickListener()
        {
            NotificationHelper.mediaStyleNotification(this)
        }

        // Custom style Notification
        binding.btncustomStyleNotificaiton.setOnClickListener()
        {
            val title = binding.txtTitle.text.toString()
            val msg = binding.txtMessage.text.toString()
            NotificationHelper.customStyleNotification(this,title,msg)
        }

    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun showNotificationSettingsDialog(context: Context?) {
        AlertDialog.Builder(context)
            .setTitle("Enable Notifications")
            .setMessage("Notifications are disabled. Would you like to open settings to enable them?")
            .setPositiveButton(
                "Yes",
                DialogInterface.OnClickListener { dialog, which -> // Open notification settings
                    context?.let { openNotificationSetting(it) }
                })
            .setNegativeButton(
                "No",
                DialogInterface.OnClickListener { dialog, which -> // Do nothing
                    dialog.dismiss()
                })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }


    private fun setUpGlide() {
        Glide.with(this)
            .asBitmap()
            .load(R.drawable.rohit2016)
            .circleCrop()
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmap = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    bitmap = null
                }
            })
    }


}