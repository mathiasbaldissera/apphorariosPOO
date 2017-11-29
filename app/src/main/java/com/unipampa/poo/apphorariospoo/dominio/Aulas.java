package com.unipampa.poo.apphorariospoo.dominio;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by mathias on 09/11/17.
 */

public class Aulas implements Comparable<Aulas>, Serializable {
    private int dia;
    private String sala;
    private Horario hrInico;
    private Horario hrTermino;
    private long lastNotification;


    public Aulas(int dia, String sala, Horario hrInico, Horario hrTermino) {
        this.dia = dia;
        this.sala = sala;
        this.hrInico = hrInico;
        this.hrTermino = hrTermino;
    }

    public boolean equalsDia(int dia) {
        return this.dia == dia;
    }

    public int getDia() {
        return dia;
    }

    /**
     * @param obj Aula a ser comparada
     * @return -1 caso this comece antes OU comece no msm horario e termine antes de obj, +1 caso this comece depois OU comece no mesmo horario e termine depois, 0 caso comecem e terminem na mesma hor
     */
    @Override
    public int compareTo(@NonNull Aulas obj) {
        if (this.getHrInico().compareTo(obj.getHrInico()) == -1) {
            return -1;
        } else if (this.getHrInico().compareTo(obj.getHrInico()) == 1) {
            return 1;
        } else if (this.getHrTermino().compareTo(obj.getHrTermino()) == -1) {
            return -1;
        } else if (this.getHrTermino().compareTo(obj.getHrTermino()) == 1) {
            return 1;
        }
        return 0;
    }


    @Override
    public int hashCode() {
        int result = dia;
        if (sala != null) {
            result = 31 * result + sala.hashCode();
        } else {
            result = 31 * result + 0;
        }
        if (hrInico != null) {
            result = 31 * result + hrInico.hashCode();
        } else {
            result = 31 * result + 0;
        }
        if (hrTermino != null) {
            result = 31 * result + hrTermino.hashCode();
        } else {
            result = 31 * result + 0;
        }
        return result;
    }

    public String getSala() {
        return sala;
    }

    public Horario getHrInico() {
        return hrInico;
    }

    public Horario getHrTermino() {
        return hrTermino;
    }

    public void setHrInico(Horario hrInico) {
        this.hrInico = hrInico;
    }

    public void setHrTermino(Horario hrTermino) {
        this.hrTermino = hrTermino;
    }

    public void notificar(){
        Calendar c = Calendar.getInstance();
        lastNotification= (c.get(Calendar.YEAR)*1000)+c.get(Calendar.DAY_OF_YEAR);
    }

    public boolean jaFoiNotificadaHoje(){
        Calendar c = Calendar.getInstance();
        long today=(c.get(Calendar.YEAR)*1000)+c.get(Calendar.DAY_OF_YEAR);
        Log.d("today", String.valueOf(today));
        return today==lastNotification;

    }
    public Horario getHoraNotificacao(){
        int hora = hrInico.getHora();
        int minuto = hrInico.getMinuto()-15;
        if(hrInico.getMinuto()<0){
            minuto=hrInico.getMinuto()-15+60;
            hora--;

        }
        return new Horario(hora, minuto);
    }
}
