package com.example.ssidtest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val act = "android.intent.action.ACCEPT_ACTION"
        if (intent.action.equals(act)) {
            var value=intent.extras!!.getString("message")
            var intent=Intent(context, MainActivity::class.java)
            intent.putExtra("message", value)
            context.startActivity(intent)
      //      Toast.makeText(context, ""+value, Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "do not get notification ", Toast.LENGTH_SHORT).show()

        }

    }
}