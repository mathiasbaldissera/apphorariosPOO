package com.unipampa.poo.apphorariospoo.dominio;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.unipampa.poo.apphorariospoo.dominio.Disciplina;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mathias on 09/11/17.
 */

public class GerenciadorDeDisciplinas {
    private static GerenciadorDeDisciplinas ME;

    private Context context;
    private List<Disciplina> disciplinas;

    public static GerenciadorDeDisciplinas getInstance(Context context) {
        if (ME == null)
            ME = new GerenciadorDeDisciplinas(context);
        return ME;

    }


    private GerenciadorDeDisciplinas(Context context) {
        this.context = context;
        this.disciplinas = ler();

    }

    public boolean addDisciplina(Disciplina d) {
        boolean r = false;
        Log.d("tamanho antes", "" + (disciplinas.size()));
        if (d != null) {
            r = disciplinas.add(d);
            salvar();
        }
        Log.d("tamanho antes", "" + (disciplinas.size()));
        return r;

    }

    public void salvar() {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = context.openFileOutput("arquivo.bin", Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            Log.d("FileNotFoundException", "Arquivo não encontrado");
        }
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(disciplinas);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            Log.d("IOEXception", "Não foi possivel fazer algo");
        }


    }


    public List<Disciplina> ler() {
        List<Disciplina> disciplinas;
        try {

            FileInputStream fis = context.openFileInput("arquivo.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            disciplinas = (List<Disciplina>) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException e) {
            Log.d("IOException ", "Ainda não foi salvo nenhuma disciplina");
            disciplinas = new ArrayList<>();

        } catch (ClassNotFoundException e) {
            Log.d("IOException ", "Erro de classe" + e.getMessage());
            disciplinas = new ArrayList<>();

        }
        return disciplinas;


    }

    public List<Disciplina> getDisciplinasDoDia(int dia) {
        Log.d("DIscs", String.valueOf(disciplinas.size()));
        ArrayList<Disciplina> retorno = new ArrayList<>();
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.contaisAulasNoDia(dia))
                retorno.add(disciplina);
        }
        return retorno;
    }

    public Disciplina getDisciplinaByHashCode(int hashCode) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.equalsHashCode(hashCode)) {
                return disciplina;
            }
        }
        return null;
    }

    public List<Disciplina> getDisciplinasDoDiaComNotificação(int dia) {
        Log.d("DIscs", String.valueOf(disciplinas.size()));
        ArrayList<Disciplina> retorno = new ArrayList<>();
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.contaisAulasNoDia(dia) && disciplina.isNotificar())
                retorno.add(disciplina);
        }
        return retorno;
    }

    public void replaceDisciplina(int hashCode, Disciplina novaDisciplina) {
        for (int i = 0; i < disciplinas.size(); i++) {
            Disciplina disciplina = disciplinas.get(i);
            if (disciplina.equalsHashCode(hashCode)) {
                disciplinas.set(i, novaDisciplina);
                salvar();

            }

        }
    }


    public Notificacao getProximaNotificacao() {
       Notificacao notificacaoDeRetorno=null;
        for (Disciplina disc : disciplinas) {
                Aulas aula = disc.getProximaAula();
            if (notificacaoDeRetorno==null && aula!=null){
                notificacaoDeRetorno=new Notificacao(disc.getNome(), disc.getProximaAula());
            }else if (aula!=null){
                Log.d("de retorno",notificacaoDeRetorno.toString());
                Notificacao notificacao = new Notificacao(disc.getNome(), disc.getProximaAula());
                Log.d("n de retorno", notificacao.toString());
                if (notificacaoDeRetorno.compareTo(notificacao)==-1){
                    notificacaoDeRetorno=notificacao;
                }
            }
        }
        return notificacaoDeRetorno;
    }
}