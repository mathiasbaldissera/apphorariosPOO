package com.unipampa.poo.apphorariospoo.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.unipampa.poo.apphorariospoo.R;
import com.unipampa.poo.apphorariospoo.TelaInicial;

/**
 * Created by mathias on 14/11/17.
 */

public class NotificationUtils {

    private static final int NOTIFICATION_ID = 1138;
    static final String NOTIFICAR_AULA="notifcar-aula";

    public static void notificarAula(Context context, String nome, String text){
        remindUser(context,nome, text);
    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, TelaInicial.class);
        return PendingIntent.getActivity(context, 5678, startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.mipmap.logo);
        return largeIcon;

    }

    private static void remindUser(Context context, String title, String text) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.green))
                .setSmallIcon(R.mipmap.logo)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }
}
