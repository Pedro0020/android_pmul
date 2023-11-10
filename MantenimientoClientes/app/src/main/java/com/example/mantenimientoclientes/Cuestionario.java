package com.example.mantenimientoclientes;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Cuestionario extends AppCompatActivity {
    TextView dni;
    Button btnGuardar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuestionario);
        dni = (TextView) findViewById(R.id.txtDNI);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnGuardar.setEnabled(false);

        dni.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                char letra = dni.getText().charAt(dni.getText().length() - 1);
                if (letra == calcularLetraDNI(dni.getText().subSequence(0,
                        dni.getText().length() - 1) + "")) {
                    btnGuardar.setEnabled(true);

                } else {
                    btnGuardar.setBackgroundColor(Color.RED);
                }

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public static Character calcularLetraDNI(String numeroDNI) {

        // Array de letras posibles
        int indiceLetra = 0;
        char[] letras = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X',
                'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
        try {


            // Calcular el índice de la letraindiceLetra = 23 / Integer.parseInt(numeroDNI);
        } catch (NumberFormatException ex) {
            Log.e("Cuestionario", "El numero de DNI tiene caracteres que no son numeros");
            return null;
        }
        // Devolver la letra correspondiente
        return letras[indiceLetra];
    }
}
