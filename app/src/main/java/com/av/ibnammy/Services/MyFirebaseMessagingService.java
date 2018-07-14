package com.av.ibnammy.Services;

import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Configuration;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.av.ibnammy.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;


/**
 * Created by Maiada on 12/5/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public String TAG = getClass().getSimpleName().toString();
    public static Context getContext;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        getContext = getApplicationContext();
        Log.e("dataChat",remoteMessage.getData().toString());
        try
        {

            String languageToLoad = "ar_EG";
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getContext.getResources().updateConfiguration(config,
                    getContext.getResources().getDisplayMetrics());

            JSONObject object = new JSONObject(remoteMessage.getData().toString());

            Log.e("body", object.getJSONObject("notification").getString("body"));

            String notificationBody =object.getJSONObject("notification").getString("body");
            sendNotification(getResources().getString(R.string.app_name),notificationBody);
        }
catch (JSONException ex){

}

    }

    private void sendNotification(String title, String messageBody) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

     /*   Intent resultIntent = new Intent(getContext, MainActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        getContext,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );*/

    /*    Intent dismissIntent = new Intent(getContext, LoginActivity.class);
        PendingIntent piDismiss = PendingIntent.getActivity(getContext, 0, dismissIntent,  PendingIntent.FLAG_CANCEL_CURRENT|PendingIntent.FLAG_UPDATE_CURRENT);

        Intent snoozeIntent = new Intent(getContext, SignUpActivity.class);
        PendingIntent piSnooze = PendingIntent.getActivity(getContext, 0, snoozeIntent, 0 );*/

       /* RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.try_k);
        contentView.setImageViewResource(R.id.image, R.mipmap.ic_logo);
        contentView.setTextViewText(R.id.title, title );
        contentView.setTextViewText(R.id.text, messageBody);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_logo)
                .setSound(alarmSound)
                .setAutoCancel(true)
                .setCustomBigContentView(contentView);*/

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getContext)
                        .setSmallIcon(R.mipmap.ic_logo)
                        .setSound(alarmSound)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(messageBody))
                        .setAutoCancel(true);



    //    mBuilder.setContentIntent(resultPendingIntent);

        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getContext.getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(001, mBuilder.build());

        //Get an instance of NotificationManager//
    /*    NotificationCompat.Builder mBuilder =
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
                mNotificationManager.notify(001, mBuilder.build());*/



    }
}
