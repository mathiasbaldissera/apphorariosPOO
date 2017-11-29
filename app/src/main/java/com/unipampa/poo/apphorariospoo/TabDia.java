package com.unipampa.poo.apphorariospoo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.unipampa.poo.apphorariospoo.dominio.Aulas;
import com.unipampa.poo.apphorariospoo.dominio.Disciplina;
import com.unipampa.poo.apphorariospoo.dominio.GerenciadorDeDisciplinas;
import com.unipampa.poo.apphorariospoo.views.ViewAulasDias;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mathias on 24/06/2017.
 */

public class TabDia extends Fragment {
    public static final TabDia newInstance(int numeroDia) {
        TabDia tabDia = new TabDia();
        Bundle bdl = new Bundle(1);
        bdl.putInt("numeroDia", numeroDia);
        tabDia.setArguments(bdl);
        return tabDia;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_dia, container, false);
        return rootView;
    }

    public void onStart() {
        super.onStart();
        actualize();
    }

    @Override
    public void onResume() {
        super.onResume();
        actualize();

    }

    private void actualize() {
        int numeroDia = getArguments().getInt("numeroDia");
        List<Disciplina> disciplinas = GerenciadorDeDisciplinas.getInstance(getActivity()).getDisciplinasDoDia(numeroDia);
        List<ViewAulasDias> vad = new ArrayList<>();
        for (Disciplina disciplina : disciplinas) {

            List<Aulas> aulas = disciplina.getAulasDoDia(numeroDia);
            for (Aulas aula : aulas) {
                Log.d("", aula.getHrInico().toString() + "/" + aula.getHrTermino().toString());
                Bundle bundle = new Bundle();
                bundle.putString(BundleUtils.hrInicio.toString(), aula.getHrInico().toString());
                bundle.putString(BundleUtils.hrTermino.toString(), aula.getHrTermino().toString());
                bundle.putString(BundleUtils.sala.toString(), aula.getSala());
                bundle.putInt(BundleUtils.dia.toString(), aula.getDia());
                bundle.putString(BundleUtils.nome.toString(), disciplina.getNome());
                bundle.putString(BundleUtils.professor.toString(), disciplina.getProfessor());
                bundle.putString(BundleUtils.turma.toString(), disciplina.getTurma());
                bundle.putString(BundleUtils.curso.toString(), disciplina.getCurso());
                bundle.putString(BundleUtils.semestre.toString(), disciplina.getSemestre());
                bundle.putInt(BundleUtils.hashCodeDisciplina.toString(), disciplina.hashCode());

                vad.add(ViewAulasDias.newInstance(getActivity(), bundle));
            }
        }
        Collections.sort(vad);
        LinearLayout ll = getView().findViewById(R.id.tab_dia_linearLayout);
        ll.removeAllViews();
        for (ViewAulasDias sVad : vad) {
            ll.addView(sVad);

        }
    }

}
