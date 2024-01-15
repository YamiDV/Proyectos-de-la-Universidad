package com.anlusoft.listenerpushsms

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.os.Bundle
class ServiceListener : NotificationListenerService() {

    override fun onBind(intent: Intent): IBinder? {
        return super.onBind(intent)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val intent = Intent("com.anlusoft.listenerpushsms")
        val paquete = sbn.packageName.toString()
        if(paquete.equals("com.bcp.innovacxion.yapeapp")){
            try {
                val extras = sbn.notification.extras
                val text :String = extras.getCharSequence("android.text").toString()
                intent.putExtra("Notification Code", text)
                sendBroadcast(intent)
            }catch( e : Exception) {
               // intent.putExtra("Notification ERROR Code", mensaje)
               // sendBroadcast(intent)
            }
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        val activeNotifications = this.activeNotifications
        for (i in this.activeNotifications) {
            val paquete = i.packageName.toString()
            if(paquete.equals("com.bcp.innovacxion.yapeapp")){
                val extras = sbn.notification.extras
//                val title : String = extras.getString("android.title").toString()
//              var mensaje : String  =  "ONR " +paquete +" "+title+"sss"+text
                val text :String = extras.getCharSequence("android.text").toString()
                val intent = Intent("com.anlusoft.listenerpushsms")
                intent.putExtra("Notification Code", text)
                sendBroadcast(intent)
                break
            }
        }
    }

}