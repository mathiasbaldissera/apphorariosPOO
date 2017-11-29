package com.unipampa.poo.apphorariospoo.dominio;

import com.unipampa.poo.apphorariospoo.dominio.Aulas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by mathias on 09/11/17.
 */

public class Disciplina implements Serializable {
    private String nome;
    private String professor;
    private String curso;
    private String semestre;
    private String turma;
    private boolean notificar;
    private List<Aulas> aulas;


    public Disciplina(String nome, String professor, String curso, String semestre, String turma, boolean notificar, List<Aulas> aulas) {
        this.nome = nome;
        this.professor = professor;
        this.curso = curso;
        this.semestre = semestre;
        this.turma = turma;
        this.notificar = notificar;
        this.aulas = aulas;
    }

    public String getNome() {
        return nome;
    }

    public String getProfessor() {
        return professor;
    }

    public String getCurso() {
        return curso;
    }

    public String getSemestre() {
        return semestre;
    }

    public String getTurma() {
        return turma;
    }

    public boolean isNotificar() {
        return notificar;
    }

    public List<Aulas> getAulas() {
        return aulas;
    }

    public boolean contaisAulasNoDia(int dia) {
        for (Aulas aula : aulas) {
            if (aula.equalsDia(dia)) {
                return true;
            }
        }
        return false;
    }

    public List<Aulas> getAulasDoDia(int dia) {
        Collections.sort(aulas);
        List<Aulas> retorno = new ArrayList<>();
        for (Aulas aula : aulas) {
            if (aula.equalsDia(dia)) {
                retorno.add(aula);
            }
        }
        return retorno;
    }


    @Override
    public int hashCode() {
        int result = nome != null ? nome.hashCode() : 0;
        result = 31 * result + (professor != null ? professor.hashCode() : 0);
        result = 31 * result + (curso != null ? curso.hashCode() : 0);
        result = 31 * result + (semestre != null ? semestre.hashCode() : 0);
        result = 31 * result + (turma != null ? turma.hashCode() : 0);
        result = 31 * result + (notificar ? 1 : 0);
        result = 31 * result + (aulas != null ? aulas.hashCode() : 0);
        return result;

    }

    public Aulas getProximaAula() {
        Collections.sort(aulas);
        for (Aulas aula : aulas) {
            int dia = (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2);
            if (aula.getDia() == dia
                    && !aula.jaFoiNotificadaHoje()) {
                return aula;
            }
        }
        return null;
    }

    public boolean equalsHashCode(int hashCode) {
        return hashCode == this.hashCode();
    }
}
