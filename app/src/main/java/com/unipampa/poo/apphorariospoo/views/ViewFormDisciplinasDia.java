package com.unipampa.poo.apphorariospoo.views;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.unipampa.poo.apphorariospoo.R;
import com.unipampa.poo.apphorariospoo.dominio.Horario;

/**
 * TODO: document your custom view class.
 */
public class ViewFormDisciplinasDia extends LinearLayout {
    TextView hrtermino, hrinicio, textDias,statictxtini, statictxtter;
    ImageView clock1, clock2;
    LinearLayout llinicio, lltermino;
    Horario horarioInicio = new Horario(), horarioTermino = new Horario();
    EditText editSala;
    public static ViewFormDisciplinasDia newInstance(Context c, int dia) {

        ViewFormDisciplinasDia fragment = new ViewFormDisciplinasDia(c);
        fragment.setDia(dia);
        Log.d("newInstance", String.valueOf(dia));
        return fragment;
    }

    private ViewFormDisciplinasDia(Context context) {
        super(context);
        initialize(context);

    }



    private void initialize(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_disciplinas_dias, this);
        textDias = findViewById(R.id.form_disciplinas_dias_textDias);
        editSala = findViewById(R.id.form_disciplinas_dias_sala);
        hrtermino = findViewById(R.id.form_disciplinas_dias_hrtermino);
        hrinicio = findViewById(R.id.form_disciplinas_dias_hrinicio);
        llinicio = findViewById(R.id.form_disciplinas_dias_llinicio);
        lltermino = findViewById(R.id.form_disciplinas_dias_lltermino);

        listenersButtons();

    }

    private void listenersButtons() {
        llinicio.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View view) {
                final TimePicker tp = new TimePicker(ViewFormDisciplinasDia.super.getContext());
                tp.setIs24HourView(true);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Selecione o horari de Iicio").setView(tp);

                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (Build.VERSION.SDK_INT < 23) {
                            horarioInicio.setHora(tp.getCurrentHour());
                            horarioInicio.setMinuto(tp.getCurrentMinute());
                        } else {
                            horarioInicio.setHora(tp.getHour());
                            horarioInicio.setMinuto(tp.getMinute());
                        }
                        hrinicio.setText(horarioInicio.toString());

                    }
                });
                AlertDialog a = builder.create();
                a.show();
            }

        });

        lltermino.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View view) {
                final TimePicker tp = new TimePicker(getContext());
                tp.setIs24HourView(true);

                AlertDialog.Builder builder = new AlertDialog.Builder(ViewFormDisciplinasDia.this.getContext());
                builder.setTitle("Selecione o horario de termino").setView(tp);

                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (Build.VERSION.SDK_INT < 23) {
                            horarioTermino.setHora(tp.getCurrentHour());
                            horarioTermino.setMinuto(tp.getCurrentMinute());
                        } else {
                            horarioTermino.setHora(tp.getHour());
                            horarioTermino.setMinuto(tp.getMinute());
                        }
                        hrtermino.setText(horarioTermino.toString());


                    }
                });
                AlertDialog a = builder.create();
                a.show();
            }
        });

    }
    private void setDia(int dia) {
        switch (dia) {
            case 0:
                textDias.setText("Segunda-Feira");
                break;
            case 1:
                textDias.setText("Terça-Feira");
                break;
            case 2:
                textDias.setText("Quarta-Feira");
                break;
            case 3:
                textDias.setText("Quinta-Feira");
                break;
            case 4:
                textDias.setText("Sexta-Feira");
                break;
            case 5:
                textDias.setText("Sábado");
                break;
        }
    }


    //getSala

    public String getSala() {
        return editSala.getText().toString();
    }

    public void setHorarioInicio(Horario horarioInicio) {
        this.horarioInicio = horarioInicio;
        hrinicio.setText(horarioInicio.toString());

    }

    public void setHorarioTermino(Horario horarioTermino) {
        this.horarioTermino = horarioTermino;
        hrtermino.setText(horarioTermino.toString());

    }

    public Horario getHorarioInicio() {
        return horarioInicio;
    }

    public Horario getHorarioTermino() {
        return horarioTermino;
    }
}
