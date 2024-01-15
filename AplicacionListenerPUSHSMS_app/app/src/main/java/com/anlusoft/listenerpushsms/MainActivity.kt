package com.anlusoft.listenerpushsms

import android.Manifest
import android.R.attr
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.provider.Telephony
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.R.attr.tag
import android.telephony.TelephonyManager
import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager

import androidx.core.content.ContextCompat
import android.service.notification.StatusBarNotification

import android.app.Activity
import android.app.Notification

import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi


class MainActivity : AppCompatActivity() {

/*
    lateinit var smsManager: TelephonyManager
    override fun onStart() {
        super.onStart()
        Toast.makeText(this, "INICIAR STAR", Toast.LENGTH_SHORT).show()
        val intentFilter = IntentFilter("android.provider.Telephony.SMS_RECEIVED")
        intentFilter.setPriority(999);
        registerReceiver(smsStateReceiver,intentFilter)
    }
    override fun onStop() {
        super.onStop()
        unregisterReceiver(smsStateReceiver)
    }
*/
    /*
    private fun registerSmsListener() {
        val filter = IntentFilter("android.provider.Telephony.SMS_RECEIVED")
        filter.setPriority(999);
        registerReceiver(smsStateReceiver,filter)
    }
    private fun requestSmsPermission() {
        val permission: String = Manifest.permission.RECEIVE_SMS
        val grant = ContextCompat.checkSelfPermission(this, permission)
        if (grant != PackageManager.PERMISSION_GRANTED) {
            val permission_list = arrayOfNulls<String>(1)
            permission_list[0] = permission
            ActivityCompat.requestPermissions(this, permission_list, 1)
        }
    }

    private val smsStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            for (message in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                if (message == null) {
                    break
                }
                val mensaje_: String = message.displayMessageBody.toString().trim().replace(" ","%20")
                Toast.makeText(this@MainActivity, "Se inicia envio SMS", Toast.LENGTH_SHORT).show()
                val urlget : String=txt_url.text.toString() +"/exec?op=listenersms&mensaje="+mensaje_+"&callback=?";
                enviarWs(this@MainActivity,urlget)
            }
        }
    }
*/

    /*
    PRUEBAS CON WIFI BROADCAST
    lateinit var wifiManager: WifiManager
    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        registerReceiver(wifiStateReceiver, intentFilter)
    }
    override fun onStop() {
        super.onStop()
        unregisterReceiver(wifiStateReceiver)
    }

    private val wifiStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                WifiManager.WIFI_STATE_UNKNOWN)) {
                WifiManager.WIFI_STATE_ENABLED -> {
                    txt_url.setText("WiFi is ON")
                    enviarWs(this@MainActivity,"WiFi is ON")
               //     Toast.makeText(this@MainActivity, "Wifi is On", Toast.LENGTH_SHORT).show()
                }
                WifiManager.WIFI_STATE_DISABLED -> {
                    txt_url.setText("WiFi is OFF")
                    enviarWs(this@MainActivity,"WiFi is ON")
             //       Toast.makeText(this@MainActivity, "Wifi is Off", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
*/
  //  private val pushStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
    //    override fun onReceive(context: Context, intent: Intent) {
      //      Toast.makeText(context, "Entro ", Toast.LENGTH_SHORT).show()
           /* val nm = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            var statusNotifs: Array<StatusBarNotification>? = null
            try {
                statusNotifs = nm.activeNotifications
            } catch (t: Throwable) {
                // it can crash
            }
            if (statusNotifs != null) for (n in statusNotifs) {
                val notif: Notification = n.notification
                try {
                    val mensaje_: String = notif.extras.getCharSequence(Notification.EXTRA_TEXT).toString().trim().replace(" ","%20")
                    val urlget : String=txt_url.text.toString() +"/exec?op=listenersms&mensaje="+mensaje_+"&callback=?";
                    enviarWs(this@MainActivity,urlget)
                } catch (t: Throwable) {
                    // can crash
                }
            }
*/
       // }
    //}


    private fun registerPushListener() {
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.anlusoft.listenerpushsms")
        registerReceiver(pushStateReceiver, intentFilter)
    }

    private fun requestPushPermission() {
        startActivity( Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
    }

    private val pushStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val receivedNotificationCode = intent.getStringExtra("Notification Code").toString().trim().replace(" ","%20")
            Toast.makeText(this@MainActivity, receivedNotificationCode, Toast.LENGTH_SHORT).show()
            val urlget: String = txt_url.text.toString() + "?op=listenersms&mensaje=" + receivedNotificationCode + "&callback=?"
            enviarWs(this@MainActivity, urlget)
        }
    }
    private fun enviarWs(context: Context, url: String): String? {
        //val url = "https://script.google.com/macros/s/AKfycbzhoTeelpVTYKPswjvkYoEg9e1XGORwvHXWpqxzNugH7DpEhQ6X/exec?op=listenersms&mensaje=$mensaje&callback=?"
        val getResquest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Toast.makeText(context, "RESULTADO POST = $response", Toast.LENGTH_LONG).show()
            }) { error ->
            Toast.makeText(context, "ERROR POST = " + error.message, Toast.LENGTH_LONG).show()
        }
        Volley.newRequestQueue(context).add(getResquest)
        return ""
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPushPermission()
        registerPushListener()
//    requestSmsPermission()
//    registerSmsListener()
//    wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        btn_iniciar.setOnClickListener {
            if (txt_url.text.toString().equals("")){
                Toast.makeText(this, "Ingrese URL API Google nuevo ", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Se inicio la captura de los PUSH Notificacion", Toast.LENGTH_SHORT).show()
                txt_url.isEnabled=false
                 btn_iniciar.isEnabled=false
            }
        }

        btn_cerrar.setOnClickListener {
            Toast.makeText(this, "Se deshabilita la captura de PUSH Notificacion", Toast.LENGTH_SHORT).show()
            txt_url.isEnabled=true
            btn_iniciar.isEnabled=true
        }
    }
}