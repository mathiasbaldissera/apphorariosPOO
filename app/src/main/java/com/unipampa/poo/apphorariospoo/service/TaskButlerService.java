package com.unipampa.poo.apphorariospoo.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.unipampa.poo.apphorariospoo.BundleUtils;
import com.unipampa.poo.apphorariospoo.dominio.GerenciadorDeDisciplinas;
import com.unipampa.poo.apphorariospoo.dominio.Notificacao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by mathias on 15/11/17.
 */
public class TaskButlerService extends WakefulIntentService {

    public TaskButlerService() {
        super("TaskButlerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GerenciadorDeDisciplinas g = GerenciadorDeDisciplinas.getInstance(this);
        int dia = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2;
      /*  Notificacao n = new Notificacao("kk", new Aulas(3, "84", new Horario(Calendar.getInstance().get(Calendar.HOUR), Calendar.getInstance().get(Calendar.MINUTE)),
                new Horario(Calendar.getInstance().get(Calendar.HOUR) + 1, Calendar.getInstance().get(Calendar.MINUTE))));
       */
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        boolean stay = false;
        Notificacao n;
        do {
            n = g.getProximaNotificacao();

            Calendar currentDate = Calendar.getInstance();
            currentDate.setTime(new Date());

            if (n == null) {
                super.onHandleIntent(intent);
                return;
            }
            if ((currentDate.get(Calendar.HOUR) >= n.getAula().getHrInico().getHora())
                    && (currentDate.get(Calendar.MINUTE) > n.getAula().getHrInico().getMinuto())) {
                n.getAula().notificar();
                stay = true;

            } else {
                stay = false;
            }

        } while (stay);

        Intent it = new Intent(this, OnAlarmReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putInt(BundleUtils.hashCodeAula.toString(), n.getAula().hashCode());
        bundle.putString(BundleUtils.nome.toString(), n.getTitleToNotification());
        bundle.putString(BundleUtils.hrInicio.toString(), n.getAula().getHrInico().toString());
        bundle.putString(BundleUtils.sala.toString(), n.getAula().getSala());

        it.putExtras(bundle);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, it, 0);
        int minute = n.getAula().getHoraNotificacao().getMinuto();
        int hour = n.getAula().getHoraNotificacao().getHora();
        long currTime = System.currentTimeMillis();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(currTime);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        Log.d("Deve Notificar: ", ""+hour+":"+minute);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);


        super.onHandleIntent(intent);
    }
}



        /*List<Notification> =

            //alarmes de aviso
            if(task.isPastDue()){
                alarm.setReminder(this, task.getID());
            }

            //alarmes de repetição
            if(task.isRepeating() && task.isCompleted()){
                task = alarm.setRepeatingAlarm(this, task.getID());
            }

            //alarmes regulares
            if(!task.isCompleted() && (task.getDateDue() >= System.currentTimeMillis())){
                alarm.setAlarm(this, task);
            }
        }
        */
