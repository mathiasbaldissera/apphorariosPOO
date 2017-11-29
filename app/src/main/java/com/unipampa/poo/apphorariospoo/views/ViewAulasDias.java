package com.unipampa.poo.apphorariospoo.views;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unipampa.poo.apphorariospoo.BundleUtils;
import com.unipampa.poo.apphorariospoo.R;
import com.unipampa.poo.apphorariospoo.dominio.Horario;

/**
 * TODO: document your custom view class.
 */
public class ViewAulasDias extends LinearLayout implements Comparable<ViewAulasDias> {
    Bundle data;

    private LinearLayout rootView;
    private TextView nome, sala, professor, turma, hrInicio, hrTermino;
    private PopupInfoAulas popupInfoAulas;
    private Horario hrI, hrT;

    public static ViewAulasDias newInstance(Activity activity,Bundle bundle) {

        ViewAulasDias fragment = new ViewAulasDias(activity);
        fragment.setData(bundle);
        fragment.initialize(activity);
        return fragment;
    }

    public void setData(Bundle data) {
        this.data = data;
    }


    private ViewAulasDias(Activity activity) {
        super(activity);
    }


    private void initialize(Activity activity) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_aulas_dias, this);
        rootView = findViewById(R.id.view_aulas_dias_rootView);
        hrI = Horario.parseHorario(data.getString("hrInicio"));
        hrT = Horario.parseHorario(data.getString("hrTermino"));
  /*      TypedValue typedValue = new TypedValue();
        if (activity.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true)) {
            LinearLayout ll = findViewById(R.id.view_aulas_dias_llColorHoras);
            Drawable m = activity.getResources().getDrawable(R.drawable.rounded_orange_shape);
            m.setColorFilter(typedValue.data, PorterDuff.Mode.MULTIPLY);
            ll.setBackground(m);
        }
*/
        nome = findViewById(R.id.view_aulas_nome);
        sala = findViewById(R.id.view_aulas_sala);
        professor =findViewById(R.id.view_aulas_profe);
        turma= findViewById(R.id.view_aulas_turma);
        hrInicio = findViewById(R.id.view_aulas_dias_hrInicio);
        hrTermino= findViewById(R.id.view_aulas_dias_hrTermino);

        nome.setText(data.getString(BundleUtils.nome.toString()));
        sala.setText(data.getString(BundleUtils.sala.toString()));
        professor.setText(data.getString(BundleUtils.professor.toString()));
        turma.setText(data.getString(BundleUtils.turma.toString()));
        hrInicio.setText(data.getString(BundleUtils.hrInicio.toString()));
        hrTermino.setText(data.getString(BundleUtils.hrTermino.toString()));


        listenersButtons(activity);

    }

    private void listenersButtons(final Activity activity) {
        rootView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupInfoAulas alertDialog = PopupInfoAulas.newInstance(data);
                alertDialog.show(activity.getFragmentManager(),"dialog info aulas");

            }
        });

    }


    @Override
    public int compareTo(@NonNull ViewAulasDias viewAulasDias) {
        if (hrI.compareTo(viewAulasDias.hrI) == -1) {
            return -1;
        } else if (hrI.compareTo(viewAulasDias.hrI) == 1) {
            return 1;
        } else if (hrT.compareTo(viewAulasDias.hrT) == -1) {
            return -1;
        } else if (hrT.compareTo(viewAulasDias.hrT) == 1) {
            return 1;
        } else {
            return 0;
        }

    }
}
