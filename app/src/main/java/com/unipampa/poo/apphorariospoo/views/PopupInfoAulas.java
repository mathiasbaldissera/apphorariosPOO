package com.unipampa.poo.apphorariospoo.views;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unipampa.poo.apphorariospoo.BundleUtils;
import com.unipampa.poo.apphorariospoo.FormDisciplinas;
import com.unipampa.poo.apphorariospoo.R;
import com.unipampa.poo.apphorariospoo.TabDia;
import com.unipampa.poo.apphorariospoo.dominio.GerenciadorDeDisciplinas;

/**
 * Created by mathias on 11/11/17.
 */

public class PopupInfoAulas extends DialogFragment {
    private ImageButton backButton, editButton, deleteButton;
    private TextView nome, hrTermino, hrInicio, professor, dia, sala, turma, curso, semestre;
    AlertDialog dialog;

    public static PopupInfoAulas newInstance(Bundle bundle) {
        PopupInfoAulas fragment = new PopupInfoAulas();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //inflate layout
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout viewGroup = getActivity().findViewById(R.id.view_info_aulas_rootView);
        View layout = inflater.inflate(R.layout.popup_info_aulas, viewGroup);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(layout);
        AlertDialog dialog = builder.create();
        this.dialog = dialog;
        //buttons
        backButton = layout.findViewById(R.id.view_info_aulas_back);
        editButton = layout.findViewById(R.id.view_info_aulas_edit);
        deleteButton = layout.findViewById(R.id.view_info_aulas_delete);
        //texts
        nome = layout.findViewById(R.id.view_info_aulas_nome);
        hrTermino = layout.findViewById(R.id.view_info_aulas_hrTermino);
        hrInicio = layout.findViewById(R.id.view_info_aulas_hrInicio);
        professor = layout.findViewById(R.id.view_info_aulas_professor);
        dia = layout.findViewById(R.id.view_info_aulas_dia);
        sala = layout.findViewById(R.id.view_info_aulas_sala);
        turma = layout.findViewById(R.id.view_info_aulas_turma);
        curso = layout.findViewById(R.id.view_info_aulas_curso);
        semestre = layout.findViewById(R.id.view_info_aulas_semestre);
        //set texts
        Bundle arguments = getArguments();
        nome.setText(arguments.getString(BundleUtils.nome.toString()));
        hrInicio.setText(arguments.getString(BundleUtils.hrInicio.toString()));
        hrTermino.setText(arguments.getString(BundleUtils.hrTermino.toString()));
        professor.setText(arguments.getString(BundleUtils.professor.toString()));
        sala.setText(arguments.getString(BundleUtils.sala.toString()));
        turma.setText(arguments.getString(BundleUtils.turma.toString()));
        curso.setText(arguments.getString(BundleUtils.curso.toString()));
        semestre.setText(arguments.getString(BundleUtils.semestre.toString()));


        String stringDia = "";
        switch (arguments.getInt(BundleUtils.dia.toString())) {
            case 0:
                stringDia = "Segunda-feira";
                break;
            case 1:
                stringDia = "Terça-feira";
                break;
            case 2:
                stringDia = "Quarta-feira";
                break;
            case 3:
                stringDia = "Quinta-feira";
                break;
            case 4:
                stringDia = "Sexta-feira";
                break;
            case 5:
                stringDia = "Sábado";
                break;
        }


        dia.setText(stringDia);
        //append functions to buttons
        listenersButtons(dialog);
        //set animagion
        dialog.getWindow().getAttributes().windowAnimations = R.style.SlideUpAnimation;

        return dialog;
    }


    private void listenersButtons(final AlertDialog dialog) {

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), FormDisciplinas.class);
                intent.putExtras(getArguments());
                getActivity().startActivity(intent);

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = {"Uma aula", "A disciplina inteira"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(PopupInfoAulas.this.getActivity());
                builder.setTitle("O que você deseja deletar?")
                        .setPositiveButton("Esta aula apenas", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                GerenciadorDeDisciplinas.getInstance(PopupInfoAulas.this.getActivity())
                                        .deletarAulaByHashCode(getArguments().getInt(BundleUtils.hashCodeAula.toString()));
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("A disciplina inteira", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                GerenciadorDeDisciplinas.getInstance(PopupInfoAulas.this.getActivity())
                                        .deletarDisciplinaByHashCode(getArguments().getInt(BundleUtils.hashCodeDisciplina.toString()));
                                dialog.dismiss();

                            }
                        })
                        .setNeutralButton("Cancelar", null)
                        .show();


            }
        });


    }
}