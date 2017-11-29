package com.unipampa.poo.apphorariospoo.dominio;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by mathias on 10/11/17.
 */

public class Horario implements Comparable<Horario>, Serializable {
    private int hora=0, minuto=0;

    public Horario(int hora, int minuto) {
        this.hora = hora;
        this.minuto = minuto;
    }

    public Horario() {
    }

    public int getHora() {
        return hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public static Horario parseHorario(String horario) {
        try {
            int hora = Integer.parseInt(horario.split(":", 2)[0]);
            int min = Integer.parseInt(horario.split(":", 2)[1]);
            return new Horario(hora, min);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        } catch (NumberFormatException e) {
            return null;
        }


    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    @Override
    public String toString() {
        if (hora < 10 && minuto < 10) {
            return "0"+ hora + ":0" + minuto;
        } else if (hora < 10) {
            return "0" + hora + ":" + minuto;
        } else if (minuto < 10) {
            return hora + ":0" + minuto;
        } else {
            return hora + ":" + minuto;
        }

    }


    /**
     * @param obj horario a ser comparado
     * @return -1 caso this comece antes de obj, +1 caso this comece depois de obj, 0 caso comecem e terminem no mesmo horario
     */
    @Override
    public int compareTo(@NonNull Horario obj) {
        if (this == obj)
            return 0;
        if (this.hora < obj.hora) {
            return -1;
        } else if (this.hora > obj.hora) {
            return 1;
        } else if (this.hora == obj.hora && this.minuto < obj.minuto) {
            return -1;
        } else if (this.hora == obj.hora && this.minuto > obj.minuto) {
            return 1;
        } else {
            return 0;
        }

    }
}
