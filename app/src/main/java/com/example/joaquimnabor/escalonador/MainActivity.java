package com.example.joaquimnabor.escalonador;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById
    Button ltgButton;

    @ViewById
    Button fprrButton;

    @ViewById
    Button ibaButton;

    @ViewById
    EditText processadores;

    @ViewById
    EditText processos;

    @ViewById
    EditText quantum;

    int algoritmoSelecionado = 0;

    @AfterViews
    void init() {
        quantum.setAlpha(0);
    }

    @Click
    void ltgButton() {
        ltgButton.setBackgroundColor(Color.GREEN);
        algoritmoSelecionado = 1;
        quantum.setAlpha(0);
    }

    @Click
    void fprrButton() {
        fprrButton.setBackgroundColor(Color.GREEN);
        algoritmoSelecionado = 2;
        quantum.setAlpha(1);
    }

    @Click
    void ibaButton() {
        ibaButton.setBackgroundColor(Color.GREEN);
        algoritmoSelecionado = 3;
        quantum.setAlpha(0);
    }

    @Click
    void startButton() {

        int qtdProcessadores = Integer.parseInt(processadores.getText().toString());

        if (algoritmoSelecionado == 0) {
            Toast.makeText(this, "Selecione um algoritmo primeiro para escalonar!", Toast.LENGTH_LONG).show();
        } else {
            if (algoritmoSelecionado == 1) {
                if (processadores.getText().toString().matches("") || processos.getText().toString().matches("")) {
                    Toast.makeText(this, "Digite um valor para Processadores e Processos", Toast.LENGTH_LONG).show();
                } else {

                    if (qtdProcessadores > 0 && qtdProcessadores < 65) {
                        LTGActivity_.intent(this).processadores(processadores.getText().toString()).processos(processos.getText().toString()).start();
                    } else {
                        Toast.makeText(this, "Digite um valor entre 1 e 64 para os Processadores", Toast.LENGTH_LONG).show();
                    }

                }
            }
            if (algoritmoSelecionado == 2) {
                if (processadores.getText().toString().matches("") || processos.getText().toString().matches("") || quantum.getText().toString().matches("")) {
                    Toast.makeText(this, "Digite um valor para: Processadores, Processos e Quantum", Toast.LENGTH_LONG).show();
                } else {
                    if (qtdProcessadores > 0 && qtdProcessadores < 65) {
                        FPRRActivity_.intent(this).processadores(processadores.getText().toString()).processos(processos.getText().toString()).quantum(quantum.getText().toString()).start();
                    } else {
                        Toast.makeText(this, "Digite um valor entre 1 e 64 para os Processadores", Toast.LENGTH_LONG).show();
                    }
                }
            }
            if (algoritmoSelecionado == 3) {
                if (processadores.getText().toString().matches("") || processos.getText().toString().matches("")) {
                    Toast.makeText(this, "Digite um valor para: Processadores, Processos e Deadline", Toast.LENGTH_LONG).show();
                } else {
                    if (qtdProcessadores > 0 && qtdProcessadores < 65) {
                        IBAActivity_.intent(this).processadores(processadores.getText().toString()).processos(processos.getText().toString()).start();
                    } else {
                        Toast.makeText(this, "Digite um valor entre 1 e 64 para os Processadores", Toast.LENGTH_LONG).show();
                    }

                }
            }
        }
    }

}

