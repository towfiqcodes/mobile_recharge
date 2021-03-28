package com.example.ssidtest

import android.app.IntentService
import android.content.Intent
import android.util.Log

//// TODO: Rename actions, choose action names that describe tasks that this
//// IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
//const val ACTION_FOO = "com.example.ssidtest.action.FOO"
//const val ACTION_BAZ = "com.example.ssidtest.action.BAZ"
//
//// TODO: Rename parameters
//const val EXTRA_PARAM1 = "com.example.ssidtest.extra.PARAM1"
//const val EXTRA_PARAM2 = "com.example.ssidtest.extra.PARAM2"

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * TODO: Customize class - update intent actions and extra parameters.
 */
class MyIntentService : IntentService("MyIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        Log.e("se","sfsfs")
        var value=intent!!.extras!!.getString("message")
        Log.d("TAG", "Message : $value")

        var intent=Intent(this, MainActivity::class.java)
        intent.putExtra("message", value)
        startActivity(intent)
//        when (intent?.action) {
//            ACTION_FOO -> {
//                val param1 = intent.getStringExtra(EXTRA_PARAM1)
//                val param2 = intent.getStringExtra(EXTRA_PARAM2)
//                handleActionFoo(param1, param2)
//            }
//            ACTION_BAZ -> {
//                val param1 = intent.getStringExtra(EXTRA_PARAM1)
//                val param2 = intent.getStringExtra(EXTRA_PARAM2)
//                handleActionBaz(param1, param2)
//            }
//        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionFoo(param1: String, param2: String) {
        TODO("Handle action Foo")
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionBaz(param1: String, param2: String) {
        TODO("Handle action Baz")
    }
}