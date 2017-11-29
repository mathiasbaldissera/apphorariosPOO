package com.unipampa.poo.apphorariospoo.utils;

/**
 * Created by mathias on 23/11/17.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.unipampa.poo.apphorariospoo.R;

import static android.content.Context.MODE_PRIVATE;

public class PreferenceUtils {


    public static void mudarTema(Context context, int tema) {
        salvarNasPreferences(context, "tema_padrao", tema);
    }

    public static int pegarTema(Context context) {
        return pegarDasPreferences(context, "tema_padrao", R.style.GreenTheme_NoActionBar);
    }

    /**
     * Chamado para salvar o valor fornecido nas preferências compartilhadas em relação a determinada chave.
     *
     * @param context Context da activity que chama o metodo
     * @param chave   Chave do valor a ser salvo
     * @param valor   Valor a ser salvo
     */
    private static void salvarNasPreferences(Context context, String chave, int valor) {
        final SharedPreferences.Editor editor = context.getSharedPreferences("apphorarios", MODE_PRIVATE).edit();
        //final SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(chave, valor);
        editor.commit();
    }

    /**
     * Chamada para recuperar o valor necessário das preferências compartilhadas, identificadas por determinada chave.
     * O valor padrão será retornado de nenhum valor encontrado ou ocorreu um erro.
     *
     * @param context     Context da activity que chama o metodo
     * @param chave       Chave do valor a pego
     * @param valorPadrao Valor a ser retornado caso o valor não esteja salvo
     * @return Return the value found against given key, default if not found or any error occurs
     */
    private static int pegarDasPreferences(Context context, String chave, int valorPadrao) {
        SharedPreferences sharedPrefs = context.getSharedPreferences("apphorarios", MODE_PRIVATE);
        try {
            return sharedPrefs.getInt(chave, valorPadrao);
        } catch (Exception e) {
            e.printStackTrace();
            return valorPadrao;
        }
    }
}

