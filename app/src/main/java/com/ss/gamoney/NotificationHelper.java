package com.ss.gamoney;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHelper {

    public static void displayNotifications(Context context, String title , String body){

        Intent intent = new Intent(context,Tournaments.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,intent,PendingIntent.FLAG_CANCEL_CURRENT );
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context,Tournaments.CHANNEL_ID)
                        .setSmallIcon(R.drawable.logo_notification)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from(context);
        mNotificationMgr.notify(1,mBuilder.build());
    }
}
