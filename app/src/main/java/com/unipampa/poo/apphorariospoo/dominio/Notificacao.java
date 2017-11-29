package com.unipampa.poo.apphorariospoo.dominio;

import android.support.annotation.NonNull;

/**
 * Created by mathias on 16/11/17.
 */

public class Notificacao implements Comparable<Notificacao>{
    String nomeDisc;
    Aulas aula;
    public Notificacao(String nomeDisc, Aulas aula){
        this.nomeDisc=nomeDisc;
        this.aula=aula;


    }

    @Override
    public String toString() {
        return "Notificacao{" +
                "nomeDisc='" + nomeDisc + '\'' +
                ", aula=" + aula +
                '}';
    }

    public String getTitleToNotification(){
        return nomeDisc;
    }
    public String getTextToNotification(){
        return "Às "+aula.getHrInico().toString()+", local: "+aula.getSala();
    }

    @Override
    public int compareTo(@NonNull Notificacao notificacao) {
        return aula.compareTo(notificacao.aula);
    }

    public Aulas getAula() {
        return aula;
    }

}
