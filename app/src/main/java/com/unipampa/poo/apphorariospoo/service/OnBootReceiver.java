package com.unipampa.poo.apphorariospoo.service;

/**
 * Created by mathias on 15/11/17.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * BroadCastReceiver para android.intent.action.BOOT_COMPLETED
 * passa toda a responsabilidade para TaskButlerService.
 *
 * @author Dhimitraq Jorgji
 */
public class OnBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        WakefulIntentService.acquireStaticLock(context); //acquire a partial WakeLock
        context.startService(new Intent(context, TaskButlerService.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)); //start TaskButlerService
    }
}
