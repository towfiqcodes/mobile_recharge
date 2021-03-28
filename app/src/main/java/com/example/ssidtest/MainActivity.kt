package com.example.ssidtest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : AppCompatActivity() {
    var TAG="MainActivity"

    var textView:TextView?=null

    var receiver:MyReceiver?=null
    var intentFilter:IntentFilter?=null


    /*=============================================================================================
   Inner Broadcast Receiver  - Notify  for accepting trip request by Server.
  =============================================================================================*/
    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(c: Context, intent: Intent) {
            /* for later version */

            Log.d(TAG, "accepting trip request")
           // Log.e(TAG, "FCM Message HomeActivity: $message")



       //     val jsonParser = JSONParser()
//            if (jsonParser.isValidData(message)) {
//                jsonParser.setJSONObject(message)
//                val msg: String = jsonParser.getStringValueFromJsonObj(JSONDataKey.MSG)
//                if (jsonParser.getStringValueFromJsonObj(JSONDataKey.ACTION)
//                        .equals(StaticValue.NEW_CONTENT_NOTIFICATION)
//                ) {
//                    //sharedPrefsHandler.setEzzyrMessageCount(sharedPrefsHandler.getEzzyrMessageCount() + 1);
//                    if (sharedPrefsHandler.getEzzyrMessageCount() > 0) {
//                        tv_notification_bell.setText(
//                            sharedPrefsHandler.getEzzyrMessageCount().toString() + ""
//                        )
//                        tv_notification_bell.setVisibility(View.VISIBLE)
//                    }
//                    if (inAppNotification == 1) {
//                        if (InternetConnection.isConnectedToInternet(this@DashBoardActivity)) {
//                            tv_notification_bell.setVisibility(View.GONE)
//                            sharedPrefsHandler.setEzzyrMessageCount(0)
//                        } else {
//                            AlertMessage.showMessage(
//                                this@DashBoardActivity,
//                                StaticValue.LOKKHO,
//                                StaticValue.NO_CONNECTION
//                            )
//                        }
//                        inAppNotification = 0
//                    }
//                }
//            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
   //     EventBus.getDefault().register(this)
        // Local Broadcast receiver for fcm
//        this.registerReceiver(
//            mReceiver,
//            IntentFilter(MyFirebaseInstanceIDService.LOCAL_BR_FCM_NOTIFICATION)
//        )
        textView=findViewById(R.id.textview)

        receiver= MyReceiver()
        intentFilter= IntentFilter("android.intent.action.ACCEPT_ACTION")


        val extras = intent.extras
        if (extras!=null){
            val message = extras!!.getString("message")
            textView!!.text=message
        }


//       var message = intent.getStringExtra("message");
//        if (!message.isNullOrEmpty()){
//            textView!!.text=message;
//        }else{
//            return
//        }




    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun doThis(intentServiceResult: IntentServiceResult) {
        textView!!.text=intentServiceResult.msg
        Log.e("doThis", intentServiceResult.msg)
        Toast.makeText(this, intentServiceResult.msg, Toast.LENGTH_SHORT).show()
    }


    private fun checkGooglePlayServices(): Boolean {
        // 1
        val status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        // 2
        return if (status != ConnectionResult.SUCCESS) {
            Log.e(TAG, "Error")
            // ask user to update google play services and manage the error.
            false
        } else {
            // 3
            Log.i(TAG, "Google play services updated")
            true
        }
    }


    override fun onResume() {
        super.onResume()
        registerReceiver(receiver,intentFilter)
    }


    override fun onDestroy() {
        super.onDestroy()
     //   EventBus.getDefault().register(this)
      //  unregisterReceiver(receiver);
    }

}