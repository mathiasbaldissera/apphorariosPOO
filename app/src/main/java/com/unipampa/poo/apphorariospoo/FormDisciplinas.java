package com.unipampa.poo.apphorariospoo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.unipampa.poo.apphorariospoo.dominio.Aulas;
import com.unipampa.poo.apphorariospoo.dominio.Disciplina;
import com.unipampa.poo.apphorariospoo.dominio.GerenciadorDeDisciplinas;
import com.unipampa.poo.apphorariospoo.service.OnDiscAddReceiver;
import com.unipampa.poo.apphorariospoo.utils.PreferenceUtils;
import com.unipampa.poo.apphorariospoo.views.ViewFormDisciplinasDia;

import java.util.ArrayList;
import java.util.List;

public class FormDisciplinas extends AppCompatActivity {
    private CheckBox mCbNotificar;
    private EditText mTextNomeDisc, mTextProf, mTextCurso, mTextSemestre, mTextTurma;
    private ViewFormDisciplinasDia[] mViewFormDisciplinasDiasArray;
    private CheckBox[] mCheckBoxesArray = new CheckBox[6];
    private int checkedCheckBoxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.show_from_right_animation, R.anim.close_to_left_animation);
        setTheme(PreferenceUtils.pegarTema(this));
        setContentView(R.layout.activity_form_disciplinas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.form_disciplinas_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Adicionar Disciplina");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mTextNomeDisc = (EditText) findViewById(R.id.form_disciplinas_edit_nome);
        mTextProf = (EditText) findViewById(R.id.form_disciplinas_edit_professor);
        mTextCurso = (EditText) findViewById(R.id.form_disciplinas_edit_curso);
        mTextSemestre = (EditText) findViewById(R.id.form_disciplinas_edit_semestre);
        mTextTurma = (EditText) findViewById(R.id.form_disciplinas_edit_turma);
        mCbNotificar = (CheckBox) findViewById(R.id.form_disciplinas_cb_notificacao);
        mCheckBoxesArray[0] = (CheckBox) findViewById(R.id.form_disciplinas_cb_seg);
        mCheckBoxesArray[1] = (CheckBox) findViewById(R.id.form_disciplinas_cb_ter);
        mCheckBoxesArray[2] = (CheckBox) findViewById(R.id.form_disciplinas_cb_qua);
        mCheckBoxesArray[3] = (CheckBox) findViewById(R.id.form_disciplinas_cb_qui);
        mCheckBoxesArray[4] = (CheckBox) findViewById(R.id.form_disciplinas_cb_sex);
        mCheckBoxesArray[5] = (CheckBox) findViewById(R.id.form_disciplinas_cb_sab);

        Bundle extras = getIntent().getExtras();
        Disciplina disciplina = null;
        if (extras != null) {
            disciplina = GerenciadorDeDisciplinas.getInstance(this).getDisciplinaByHashCode(extras.getInt("hashCodeDisciplina"));
            mTextNomeDisc.setText(extras.getString(BundleUtils.nome.toString()));
            mTextProf.setText(extras.getString(BundleUtils.professor.toString()));
            mTextCurso.setText(extras.getString(BundleUtils.curso.toString()));
            mTextTurma.setText(extras.getString(BundleUtils.turma.toString()));
            mTextSemestre.setText(extras.getString(BundleUtils.semestre.toString()));
            mCbNotificar.setChecked(disciplina.isNotificar());
        }
        LinearLayout lldiasHoras = (LinearLayout) findViewById(R.id.form_disciplinas_lldiasHoras);
        mViewFormDisciplinasDiasArray = new ViewFormDisciplinasDia[6];
        for (int i = 0; i < 6; i++) {
            mViewFormDisciplinasDiasArray[i] = ViewFormDisciplinasDia.newInstance(this, i);
            if (disciplina == null || !disciplina.contaisAulasNoDia(i)) {
                mViewFormDisciplinasDiasArray[i].setVisibility(View.GONE);
            } else {
                mCheckBoxesArray[i].setChecked(true);
                checkedCheckBoxes++;
                mViewFormDisciplinasDiasArray[i].setHorarioInicio(disciplina.getAulasDoDia(i).get(0).getHrInico());
                mViewFormDisciplinasDiasArray[i].setHorarioTermino(disciplina.getAulasDoDia(i).get(0).getHrTermino());
            }
            lldiasHoras.addView(mViewFormDisciplinasDiasArray[i]);
        }
        for (int i = 0; i < 6; i++) {
            Log.d("i", String.valueOf(i));
            mCheckBoxesArray[i].setOnCheckedChangeListener(getListener(i, mViewFormDisciplinasDiasArray));
        }
    }


    public CompoundButton.OnCheckedChangeListener getListener(final int i, final ViewFormDisciplinasDia[] fdda) {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    fdda[i].setVisibility(View.VISIBLE);
                    TextInputLayout diasError = (TextInputLayout) findViewById(R.id.form_disciplinas_dias_forWarning);
                    diasError.setError(null);
                    checkedCheckBoxes++;
                } else {
                    fdda[i].setVisibility(View.GONE);
                    checkedCheckBoxes--;
                }
            }
        };
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.form_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.form_disciplinas_confirm) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            String nomeDisciplina = this.mTextNomeDisc.getText().toString();
            if (nomeDisciplina.equals("")) {
                TextInputLayout nomeTil = (TextInputLayout) findViewById(R.id.form_disciplinas_edit_nome_container);
                nomeTil.setError("Este campo não pode estar vazio!");
            } else {
                TextInputLayout nomeTil = (TextInputLayout) findViewById(R.id.form_disciplinas_edit_nome_container);
                nomeTil.setError(null);
            }
            if (checkedCheckBoxes < 1) {
                TextInputLayout diasError = (TextInputLayout) findViewById(R.id.form_disciplinas_dias_forWarning);
                diasError.setError("Selecione ao menos um dia");
            } else {
                TextInputLayout diasError = (TextInputLayout) findViewById(R.id.form_disciplinas_dias_forWarning);
                diasError.setError(null);
            }
            if (checkedCheckBoxes >= 1 && !nomeDisciplina.equals("")) {

                for (int i = 0; i < 6; i++) {
                    if (mCheckBoxesArray[i].isChecked()) {
                        if (mViewFormDisciplinasDiasArray[i].getHorarioInicio().compareTo(mViewFormDisciplinasDiasArray[i].getHorarioTermino()) >= 0) {
                            builder.setMessage("O horario de termino da aula deve ser após o horario de início");
                            builder.setTitle("Horários Incorretos");
                            builder.setPositiveButton("OK", null).show();
                            return true;
                        }
                    }
                }

                List<Aulas> aulas = new ArrayList<>();
                for (int i = 0; i < 6; i++) {
                    if (mCheckBoxesArray[i].isChecked()) {
                        aulas.add(new Aulas(i, mViewFormDisciplinasDiasArray[i].getSala(), mViewFormDisciplinasDiasArray[i].getHorarioInicio(), mViewFormDisciplinasDiasArray[i].getHorarioTermino()));
                    }
                }
                if (aulas != null) {
                    String professor = mTextProf.getText().toString();
                    String curso = mTextCurso.getText().toString();
                    String semestre = mTextSemestre.getText().toString();
                    String turma = mTextTurma.getText().toString();
                    Disciplina novaDisciplina = new Disciplina(nomeDisciplina, professor, curso, semestre, turma, mCbNotificar.isChecked(), aulas);
                    Bundle extras = getIntent().getExtras();
                    if (extras==null) {
                        GerenciadorDeDisciplinas.getInstance(this).addDisciplina(novaDisciplina);
                    }else{
                        GerenciadorDeDisciplinas.getInstance(this).replaceDisciplina(extras.getInt(BundleUtils.hashCodeDisciplina.toString()), novaDisciplina);
                    }
                finish();
                }
            }


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.show_from_left_animation, R.anim.close_to_right_animation);
        Intent intent = new Intent(this, OnDiscAddReceiver.class);
        sendBroadcast(intent);
    }
}
