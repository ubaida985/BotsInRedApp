package com.example.botsinred.utilities;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.botsinred.R;
import com.example.botsinred.activities.AlarmActivity;

public class AlarmReceiver extends BroadcastReceiver {
    public static int reqCode = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent ( context , AlarmActivity.class ) ;
        intent.setFlags ( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK ) ;
        PendingIntent pendingIntent = PendingIntent.getActivity ( context , reqCode, i, PendingIntent.FLAG_IMMUTABLE );

        NotificationCompat.Builder builder = new NotificationCompat.Builder( context , "RedBots" )
                .setSmallIcon ( R.drawable.icon_profile )
                .setContentTitle ( "Dose" )
                .setContentText ( "Time to take your daily dose" )
                .setAutoCancel ( true )
                .setDefaults ( NotificationCompat.DEFAULT_ALL )
                .setPriority ( NotificationCompat.PRIORITY_HIGH )
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from( context );
        notificationManagerCompat.notify ( 123 , builder.build ( ) ) ;

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
