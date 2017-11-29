package com.unipampa.poo.apphorariospoo.views;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.unipampa.poo.apphorariospoo.R;
import com.unipampa.poo.apphorariospoo.utils.PreferenceUtils;

/**
 * Created by mathias on 10/11/17.
 */

public class PopupColorChange extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
     final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout viewGroup = (RelativeLayout) getActivity().findViewById(R.id.pull);

        View layout = inflater.inflate(R.layout.popup_colors, viewGroup);
        builder.setView(layout);
        builder.setTitle("Selecione a cor do aplicativo");
        final AlertDialog a = builder.create();

        Button cancel = layout.findViewById(R.id.color_btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a.dismiss();
            }
        });


        FloatingActionButton b = layout.findViewById(R.id.color_btn_blue);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceUtils.mudarTema(getActivity(), R.style.BlueTheme_NoActionBar);
                a.dismiss();
                getActivity().recreate();

            }
        });


        FloatingActionButton g = layout.findViewById(R.id.color_btn_green);
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceUtils.mudarTema(getActivity(), R.style.GreenTheme_NoActionBar);
                a.dismiss();
                getActivity().recreate();


            }
        });


        FloatingActionButton r = layout.findViewById(R.id.color_btn_red);
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceUtils.mudarTema(getActivity(), R.style.RedTheme_NoActionBar);

                a.dismiss();
                getActivity().recreate();


            }
        });


        FloatingActionButton o = layout.findViewById(R.id.color_btn_cian);
        o.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceUtils.mudarTema(getActivity(), R.style.OrangeTheme_NoActionBar);
                a.dismiss();
                getActivity().recreate();


            }
        });


        FloatingActionButton p = layout.findViewById(R.id.color_btn_pink);

        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceUtils.mudarTema(getActivity(), R.style.PurpleTheme_NoActionBar);
                a.dismiss();
                getActivity().recreate();


            }
        });


        return a;

    }

}
