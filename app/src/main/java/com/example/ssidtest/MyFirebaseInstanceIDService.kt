package com.example.ssidtest

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.greenrobot.eventbus.EventBus


class MyFirebaseInstanceIDService : FirebaseMessagingService() {
      var TAG="MyFirebaseInstanceIDService"

    companion object{
        val LOCAL_BR_FCM_NOTIFICATION = "MyFirebaseInstanceIDService"

    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob();


            val title = remoteMessage.data["title"]
            val message = remoteMessage.data["message"]

            EventBus.getDefault().post(IntentServiceResult(title!!, message!!));


            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            Log.d(TAG, "Message : $message")
//            val intent = Intent(this@MyFirebaseInstanceIDService, MainActivity::class.java)
//            intent.putExtra("isNotification", true)
//            // intent.putExtra("title", value1);
//            // intent.putExtra("title", value1);
//            intent.putExtra("message", message)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//         //   startActivity(intent)
            Log.d("INTENT-TITLE", title!!)
            Log.d("INTENT-MESSAGE", message!!)
//            var intent=Intent(this, MyIntentService::class.java)
//            intent.putExtra("message",message);
//            startService(intent)


//            val bi = Intent(LOCAL_BR_FCM_NOTIFICATION)
//            bi.putExtra("message", message)
//            sendBroadcast(bi)
//                if(remoteMessage.getData().get("activity_value")!=null && !remoteMessage.getData().get("activity_value").isEmpty())
//                    threeDays = remoteMessage.getData().get("activity_value");

//                if(remoteMessage.getData().get("activity_value")!=null && !remoteMessage.getData().get("activity_value").isEmpty())
//                    threeDays = remoteMessage.getData().get("activity_value");


        } else {
            // Handle message within 10 seconds
            handleNow();
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.notification!!.body);

            showNotification(
                remoteMessage.notification!!.title,
                remoteMessage.notification!!.body
            );
        }

    }


    override fun onNewToken(s: String) {
        super.onNewToken(s)
        Log.d(TAG, "Refreshed token: $s")
        ///Toasty.success(this,"token"+ s, Toasty.LENGTH_SHORT).show();
        sendRegistrationToServer(s)
    }
    private fun scheduleJob() {
        // [START dispatch_job]
        val work = OneTimeWorkRequest.Builder(MyWorker::class.java).build()
        WorkManager.getInstance().beginWith(work).enqueue()
        // [END dispatch_job]
    }
    private fun handleNow() {
        Log.d(TAG, "Short lived task is done.")
    }


    private fun sendRegistrationToServer(token: String) {
        val sharedPrefsHandler = SharedPrefsHandler(this)
        sharedPrefsHandler.putString(Constants.KEY_FCM_TOKEN, token)
    }

    //    @SuppressLint("LongLogTag")
    //    private void handleNow(String message, String title) {
    //        Log.d(TAG, "Short lived task is done.");
    //        SharedPrefsHandler sharedPrefsHandler = new SharedPrefsHandler(this);
    //        Log.e("json", ">>>>>>>>>>>>>>>>>>>>>" + "  " + message);
    //        JSONParser jsonParser = new JSONParser();
    //        if (jsonParser.isValidData(message)) {
    //            jsonParser.setJSONObject(message);
    //            Intent bi = new Intent(LOCAL_BR_FCM_NOTIFICATION);
    //            bi.putExtra("message", message);
    //            sendBroadcast(bi);
    //            sendNotification(message);
    //        }
    //        else {
    //            sendNotificationForeground( message, title);
    //        }
    //    }
    //    private int getNotificationIcon(NotificationCompat.Builder notificationBuilder) {
    //        //int color = getResources().getColor(R.color.green);
    //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
    //            int color = 0x008000;
    //            notificationBuilder.setColor(color);
    //            return R.mipmap.ic_provati;
    //        }
    //        return R.mipmap.ic_provati;
    //    }
    //    @SuppressLint("LongLogTag")
    //    private void sendNotification() {
    //        int notificationId = createID();
    //
    //        String messageTitle = "title";
    //        String messageDescription = "body";
    //        PendingIntent pendingIntent = null;
    //        JSONParser jsonParser = new JSONParser();
    //        Log.e(TAG,"title"+messageTitle+"body"+messageDescription);
    //
    //        String channelId = getString(R.string.default_notification_channel_id);
    //        Intent intent = new Intent(this, NotificationActivity.class);
    //        intent.putExtra(StaticValue.MESSAGE, messageBody);
    //        SharedPrefsHandler sharedPrefsHandler = new SharedPrefsHandler(this);
    //
    //        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    //        pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
    //                PendingIntent.FLAG_ONE_SHOT);
    //
    //        NotificationCompat.Builder notificationBuilder =
    //                new NotificationCompat.Builder(this, channelId);
    //        notificationBuilder.setAutoCancel(true);
    //        if (messageBody != null && messageBody.length() > 0) {
    //            jsonParser.setJSONObject(messageBody);
    //            String msg = jsonParser.getStringValueFromJsonObj(JSONDataKey.MSG);
    //            notificationBuilder.setContentTitle(messageTitle);
    //
    //                JSONParser jsonParser1 = new JSONParser();
    //            //    sharedPrefsHandler.setEzzyrMessageCount(sharedPrefsHandler.getEzzyrMessageCount() + 1);
    //                if (jsonParser1.isValidData(msg)) {
    //                    jsonParser1.setJSONObject(msg);
    //                    messageTitle = jsonParser1.getStringValueFromJsonObj(JSONDataKey.TITLE);
    //                    messageDescription = jsonParser1.getStringValueFromJsonObj(JSONDataKey.DESCRIPTION);
    //                    Log.e(TAG,"title"+messageTitle+"body"+messageDescription);
    //                   // sharedPrefsHandler.setNotificationKEY(notification_Key);
    //                    intent.putExtra(StaticValue.EXTRA_NOTIFICATION_TYPE, StaticValue.NEW_CONTENT_NOTIFICATION);
    //                    intent.putExtra(StaticValue.TAG_IN_APP_NOTIFICATION, 1);
    //                    intent.putExtra("title", messageTitle);
    //                    intent.putExtra("description", messageDescription);
    //                    notificationBuilder.setContentTitle(messageTitle);
    //                    notificationBuilder.setSmallIcon(R.mipmap.ic_provati);
    //                    intent.putExtra(StaticValue.MESSAGE, msg);
    //            }
    //            notificationBuilder.setContentIntent(pendingIntent);
    //            notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder));
    //            NotificationManager notificationManager =
    //                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    //            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    //                NotificationChannel channel = new NotificationChannel(channelId,
    //                        "lokkho",
    //                        NotificationManager.IMPORTANCE_DEFAULT);
    //                notificationManager.createNotificationChannel(channel);
    //            }
    //            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    //        }
    //
    //
    //      /*  NotificationManager notificationManager =
    //                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    //
    //        // Since android Oreo notification channel is needed.
    //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    //            NotificationChannel channel = new NotificationChannel(channelId,
    //                    "Channel human readable title",
    //                    NotificationManager.IMPORTANCE_DEFAULT);
    //            notificationManager.createNotificationChannel(channel);
    //        }
    //
    //        notificationManager.notify(0 *//* ID of notification *//*, notificationBuilder.build());*/
    //    }
    //
    //    @SuppressLint("LongLogTag")
    //    private void sendNotificationForeground(String messageBody, String title) {
    //        int notificationId = createID();
    //
    //        String messageTitle = "title";
    //        String messageDescription = "body";
    //        PendingIntent pendingIntent = null;
    //        JSONParser jsonParser = new JSONParser();
    //        Log.e(TAG,"title"+messageTitle+"body"+messageDescription);
    //
    //        String channelId = getString(R.string.default_notification_channel_id);
    //        Intent intent = new Intent(this, NotificationActivity.class);
    //        intent.putExtra(StaticValue.MESSAGE, messageBody);
    //        SharedPrefsHandler sharedPrefsHandler = new SharedPrefsHandler(this);
    //
    //        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    //        pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
    //                PendingIntent.FLAG_ONE_SHOT);
    //
    //        NotificationCompat.Builder notificationBuilder =
    //                new NotificationCompat.Builder(this, channelId);
    //        notificationBuilder.setAutoCancel(true);
    //        if (messageBody != null && messageBody.length() > 0) {
    //            jsonParser.setJSONObject(messageBody);
    //            //String msg = jsonParser.getStringValueFromJsonObj(JSONDataKey.MSG);
    //            notificationBuilder.setContentTitle(messageTitle);
    //
    //            JSONParser jsonParser1 = new JSONParser();
    //            //    sharedPrefsHandler.setEzzyrMessageCount(sharedPrefsHandler.getEzzyrMessageCount() + 1);
    //            if (messageBody!=null) {
    //               // jsonParser1.setJSONObject(msg);
    //                //messageTitle = jsonParser1.getStringValueFromJsonObj(JSONDataKey.TITLE);
    //                //messageDescription = jsonParser1.getStringValueFromJsonObj(JSONDataKey.DESCRIPTION);
    //                Log.e(TAG,"title"+messageTitle+"body"+messageDescription);
    //                // sharedPrefsHandler.setNotificationKEY(notification_Key);
    //                intent.putExtra(StaticValue.EXTRA_NOTIFICATION_TYPE, StaticValue.NEW_CONTENT_NOTIFICATION);
    //                intent.putExtra(StaticValue.TAG_IN_APP_NOTIFICATION, 1);
    //                intent.putExtra("title", messageTitle);
    //                intent.putExtra("description", messageDescription);
    //                notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody));
    //                notificationBuilder.setContentTitle(title + " " + messageBody);
    //                notificationBuilder.setSmallIcon(R.mipmap.ic_provati);
    //                intent.putExtra(StaticValue.MESSAGE, "msg");
    //            }
    //            notificationBuilder.setContentIntent(pendingIntent);
    //            notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder));
    //            NotificationManager notificationManager =
    //                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    //            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    //                NotificationChannel channel = new NotificationChannel(channelId,
    //                        "lokkho",
    //                        NotificationManager.IMPORTANCE_DEFAULT);
    //                notificationManager.createNotificationChannel(channel);
    //            }
    //            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    //        }
    //
    //
    //      /*  NotificationManager notificationManager =
    //                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    //
    //        // Since android Oreo notification channel is needed.
    //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    //            NotificationChannel channel = new NotificationChannel(channelId,
    //                    "Channel human readable title",
    //                    NotificationManager.IMPORTANCE_DEFAULT);
    //            notificationManager.createNotificationChannel(channel);
    //        }
    //
    //        notificationManager.notify(0 *//* ID of notification *//*, notificationBuilder.build());*/
    //    }
//    private fun sendNotification(messageBody: String, type: String, itemId: String, title: String) {
//        //    Intent intent = new Intent(this, DashboardActivity.class);
//        var intent: Intent? = null
//        val channelId = "fcm_default_channel"
////        intent = if (type == "delivery") {
////            Intent(this, DeliveryDashboardActivity::class.java)
////        } else Intent(this, PickDashboardActivity::class.java)
////        intent.putExtra("title", title)
////        intent.putExtra("itemId", itemId)
////        intent.putExtra("type", type)
////        intent.putExtra("body", messageBody)
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT)
//        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//        val notificationBuilder = NotificationCompat.Builder(this, channelId)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle(title) //.setContentText(messageBody)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent)
//        notificationBuilder.setStyle(NotificationCompat.BigTextStyle().bigText(messageBody))
//        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//
//        // Since android Oreo notification channel is needed.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(channelId,
//                    "Channel human readable title",
//                    NotificationManager.IMPORTANCE_DEFAULT)
//            notificationManager.createNotificationChannel(channel)
//        }
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
//    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun showNotification(
        title: String?,
        message: String?
    ) {
        // Pass the intent to switch to the MainActivity
        val intent = Intent(this, MyIntentService::class.java)
        // Assign channel ID
        val channel_id = "notification_channel"
        // Here FLAG_ACTIVITY_CLEAR_TOP flag is set to clear
        // the activities present in the activity stack,
        // on the top of the Activity that is to be launched
//        intent.action = Intent.ACTION_MAIN;
//        intent.addCategory(Intent.CATEGORY_LAUNCHER);
// Pass the intent to PendingIntent to start the
     //   intent.action = "android.intent.action.ACCEPT_ACTION";
        intent.putExtra("message", message)

        // next Activity
        val pendingIntent = PendingIntent.getService(
            this,0, intent,0
        )

        // Create a Builder object using NotificationCompat
        // class. This will allow control over all the flags
        var builder = NotificationCompat.Builder(
            applicationContext,
            channel_id
        )
                .setSmallIcon(R.drawable.sym_def_app_icon)
                .setAutoCancel(true)
                .setVibrate(
                    longArrayOf(
                        1000, 1000, 1000,
                        1000, 1000
                    )
                )
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)

        // A customized design for the notification can be
        // set only for Android versions 4.1 and above. Thus
        // condition for the same is checked here.
        builder = if (Build.VERSION.SDK_INT
                >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setContent(
                getCustomDesign(title!!, message!!)
            )
        } // If Android Version is lower than Jelly Beans,
        else {
            builder.setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.btn_dropdown)
        }
        // Create an object of NotificationManager class to
        // notify the
        // user of events that happen in the background.
        val notificationManager = getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager
        // Check if the Android Version is greater than Oreo
        if (Build.VERSION.SDK_INT
                >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channel_id, "web_app",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(
                notificationChannel
            )
        }
        notificationManager.notify(0, builder.build())
    }
    private fun getCustomDesign(
        title: String,
        message: String
    ): RemoteViews? {
        val remoteViews = RemoteViews(
            getApplicationContext().getPackageName(), R.layout.activity_list_item
        )
        remoteViews.setTextViewText(R.id.title, title)
        remoteViews.setTextViewText(R.id.message, message)
        remoteViews.setImageViewResource(
            R.id.icon,
            R.drawable.arrow_up_float
        )
        return remoteViews
    }

}