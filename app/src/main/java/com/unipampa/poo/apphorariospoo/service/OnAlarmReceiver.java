package com.unipampa.poo.apphorariospoo.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.unipampa.poo.apphorariospoo.dominio.GerenciadorDeDisciplinas;
import com.unipampa.poo.apphorariospoo.dominio.Notificacao;
import com.unipampa.poo.apphorariospoo.utils.NotificationUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mathias on 15/11/17.
 */
public class OnAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        WakefulIntentService.acquireStaticLock(context); //adquirindo WakeLock

        Bundle bundle = intent.getExtras();
        GerenciadorDeDisciplinas gerenciadorDeDisciplinas = GerenciadorDeDisciplinas.getInstance(context);
        Notificacao n = gerenciadorDeDisciplinas.getProximaNotificacao();
        n.getAula().notificar();
        gerenciadorDeDisciplinas.salvar();
        NotificationUtils.notificarAula(context, n.getTitleToNotification(),
                n.getTextToNotification());
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        Log.d("hora de notificação: ", "" + c.get(Calendar.HOUR) + "" + c.get(Calendar.MINUTE));
        //enviando notificação com taskID
        ;
      /*  Bundle bundle = intent.getExtras();
        int id = bundle.getInt(Task.EXTRA_TASK_ID);
        TasksDataSource db = TasksDataSource.getInstance(context);
        Task task = db.getTask(id);

        if(task.hasFinalDateDue() || task.getPriority() == Task.URGENT){
            notification.sendPersistentNotification(context, task); // Básica notificação
        } else {
            notification.sendBasicNotification(context, task); //Básica notificação
        }
*/
        context.startService(new Intent(context, TaskButlerService.class)); //iniciando TaskButlerService
    }
}