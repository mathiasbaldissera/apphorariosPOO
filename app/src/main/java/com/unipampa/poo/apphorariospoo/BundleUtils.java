package com.unipampa.poo.apphorariospoo;

/**
 * Created by mathias on 14/11/17.
 */

public enum BundleUtils {
    hrInicio("hrInicio"), hrTermino("hrTermino"), sala("sala"),
    dia("dia"), nome("nome"), professor("professor"), turma("turma"),
    curso("curso"), semestre("semestre"), hashCodeDisciplina("hashCodeDisciplina"),
    hashCodeAula("hashCodeAula");

    private final String text;
    private BundleUtils(final String text) {
        this.text = text;
    }
    @Override
    public String toString() {
        return text;
    }
}
