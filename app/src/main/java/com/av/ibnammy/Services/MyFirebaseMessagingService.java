package com.av.ibnammy.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.av.ibnammy.R;
import com.av.ibnammy.activities.MainActivityAya;
import com.av.ibnammy.homePage.map.MapFragment;
import com.av.ibnammy.viewprofile.Profile;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.Random;


/**
 * Created by Maiada on 12/5/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public String TAG = getClass().getSimpleName().toString();
    public static Context getContext;
    Profile profile = new Profile();
    NotificationActivity notificationActivity= new NotificationActivity();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        getContext = getApplicationContext();
        Log.e("dataChat",remoteMessage.getData().toString());
        try
        {

            JSONObject object      = new JSONObject(remoteMessage.getData().toString());
            JSONObject  dataObject = object.getJSONObject("data");

            if(!dataObject.getString("First_Name").equals("null")||
                    !dataObject.getString("Second_Name").equals("null")
                    ){
                profile.setUserName(dataObject.getString("First_Name").replaceAll("\n","")
                        +" "+dataObject.getString("Second_Name").replaceAll("\n",""));


            }else {
                profile.setUserName("لا يوجد");
                profile.setFullUserName("لا يوجد");
            }

            if(!dataObject.getString("Mobile").equals("null"))
            {
                profile.setMobile(dataObject.getString("Mobile"));
            }
            else
            {
                profile.setMobile("لا يوجد");
            }


            if(!dataObject.getString("Latitude").equals("null"))
                profile.setHomeLatitude(dataObject.getString("Latitude"));

            if(!dataObject.getString("Longitude").equals("null"))
                profile.setHomeLongitude(dataObject.getString("Longitude"));

            String notificationBody =object.getJSONObject("notification").getString("body");
            profile.setSubscriptionStatus(notificationBody);

            sendNotification(getResources().getString(R.string.app_name),notificationBody);
        }
catch (JSONException ex){

}

    }

    private void sendNotification(String title, String notificationBody) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        int notificationId = new Random().nextInt(); // just use a counter in some util class...
        PendingIntent dismissIntent = notificationActivity.getDismissIntent(notificationId, getContext,profile);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext);
        builder.setPriority(NotificationCompat.PRIORITY_MAX) //HIGH, MAX, FULL_SCREEN and setDefaults(Notification.DEFAULT_ALL) will make it a Heads Up Display Style
                .setDefaults(Notification.DEFAULT_ALL) // also requires VIBRATE permission
                .setSmallIcon(R.mipmap.ic_logo)
                .setSound(alarmSound)
                .setContentTitle(title)
                .setContentText(notificationBody)
                .setStyle(new NotificationCompat.BigTextStyle()
                .bigText(notificationBody))
                .setAutoCancel(true)
                .addAction(R.mipmap.ic_my_alert, "تفاصيل الاستغاثة", dismissIntent);

        builder.setContentIntent(dismissIntent);

// Gets an instance of the NotificationManager service
        NotificationManager notifyMgr = (NotificationManager) getContext.getSystemService(Context.NOTIFICATION_SERVICE);

// Builds the notification and issues it.
        notifyMgr.notify(notificationId, builder.build());
    }





  /*  private void sendNotification(String title, String title) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent dismissIntent = new Intent(getContext, MainActivityAya.class);
        PendingIntent piDismiss = PendingIntent.getActivity(getContext, 0, dismissIntent,  PendingIntent.FLAG_CANCEL_CURRENT);

        Intent resultIntent = new Intent(getContext, MainActivityAya.class);
        //esultIntent.putExtra()
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        getContext,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

    *//*    Intent dismissIntent = new Intent(getContext, LoginActivity.class);
        PendingIntent piDismiss = PendingIntent.getActivity(getContext, 0, dismissIntent,  PendingIntent.FLAG_CANCEL_CURRENT|PendingIntent.FLAG_UPDATE_CURRENT);

        Intent snoozeIntent = new Intent(getContext, SignUpActivity.class);
        PendingIntent piSnooze = PendingIntent.getActivity(getContext, 0, snoozeIntent, 0 );*//*

       *//* RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.try_k);
        contentView.setImageViewResource(R.id.image, R.mipmap.ic_logo);
        contentView.setTextViewText(R.id.title, title );
        contentView.setTextViewText(R.id.text, messageBody);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_logo)
                .setSound(alarmSound)
                .setAutoCancel(true)
                .setCustomBigContentView(contentView);*//*

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getContext)
                        .setSmallIcon(R.mipmap.ic_logo)
                        .setSound(alarmSound)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(messageBody))
                        .addAction(R.mipmap.ic_my_alert,"Help Details".toLowerCase(),piDismiss)
                        .setAutoCancel(true);
      mBuilder.setContentIntent(resultPendingIntent);

        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getContext.getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(001, mBuilder.build());
       // mNotifyMgr.cancel(001);


        //Get an instance of NotificationManager//
    *//*    NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getContext)
                        .setSmallIcon(R.drawable.ic_millim_logo)
                        .setContentTitle(title)
                        .setSound(alarmSound)
                        .setContentText(messageBody);

        // Gets an instance of the NotificationManager service//
        NotificationManager mNotificationManager =
                (NotificationManager) getContext.getSystemService(Context.NOTIFICATION_SERVICE);

        // When you issue multiple notifications about the same type of event,
        // it’s best practice for your app to try to update an existing notification
        // with this new information, rather than immediately creating a new notification.
        // If you want to update this notification at a later date, you need to assign it an ID.
        // You can then use this ID whenever you issue a subsequent notification.
        // If the previous notification is still visible, the system will update this existing notification,
        // rather than create a new one. In this example, the notification’s ID is 001//
                mNotificationManager.notify(001, mBuilder.build());*//*



    }*/
}
