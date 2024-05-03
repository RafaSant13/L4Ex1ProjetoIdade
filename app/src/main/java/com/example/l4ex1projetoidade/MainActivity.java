package com.example.l4ex1projetoidade;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText etDia;
    private EditText etMes;
    private EditText etAno;
    private TextView tvIdade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etDia = findViewById(R.id.etDia);
        etDia.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        etMes = findViewById(R.id.etMes);
        etMes.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        etAno = findViewById(R.id.etAno);
        etAno.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        Button btnCalcular = findViewById(R.id.btnCalcular);
        tvIdade = findViewById(R.id.tvIdade);
        tvIdade.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        btnCalcular.setOnClickListener(op -> calc());
    }

    private void calc() {
        int diaNasc = Integer.parseInt(etDia.getText().toString());
        int mesNasc = Integer.parseInt(etMes.getText().toString());
        int anoNasc = Integer.parseInt(etAno.getText().toString());

        if (validarData(diaNasc, mesNasc, anoNasc)){
            Calendar calendar = Calendar.getInstance();
            int anoAtual = calendar.get(Calendar.YEAR);
            int mesAtual = calendar.get(Calendar.MONTH);
            int diaAtual = calendar.get(Calendar.DAY_OF_MONTH);

            int diaIdade = 0;
            int mesIdade = 0;
            int anoIdade = 0;


            if (mesAtual > mesNasc || (mesAtual == mesNasc && diaAtual >= diaNasc)){
                anoIdade = anoAtual - anoNasc;
                mesIdade = mesAtual - mesNasc;
            } else {
                anoIdade = anoAtual - anoNasc - 1;
                mesIdade = (12 - (mesNasc - mesAtual));
            }

            if (diaAtual >= diaNasc){
                diaIdade = diaAtual - diaNasc;
            } else {
                int diasMesAnterior = DiasNoMes((mesAtual-1), anoAtual);
                diaIdade = (diasMesAnterior + diaAtual) - diaNasc;
            }
            String res = anoIdade + " " + getString(R.string.anoRes) + " " + mesIdade + " " + getString(R.string.mesRes) + " " + diaIdade + " " + getString(R.string.diaRes);
            tvIdade.setText(res);
        }
        else {
            String res = getString(R.string.erro);
            tvIdade.setText(res);
        }
    }

    private boolean isBissexto (int ano){
        if (((ano % 4 == 0) && (ano % 100 != 0)) || (ano % 400 == 0)) {
            return true;
        } else {
            return false;
        }
    }

    private int DiasNoMes(int mes, int ano){
        switch (mes) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (isBissexto(ano)) {
                    return 29;
                } else {
                    return 28;
                }
        }
        return 0;
    }

    private boolean validarData (int dia, int mes, int ano){
        if ((dia < 1 || dia > 31 || mes < 1 || mes > 12)){
            return false;
        }

        if ((dia == 31 && (mes == 4 || mes == 6 || mes == 9 || mes == 11))){
            return false;
        }

        if (mes == 2 && dia > 29){
            return false;
        }

        if (mes == 2 && dia == 29 && isBissexto(ano)) {
            return false;
        }
        return true;
    }
}