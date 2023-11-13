package com.example.mantenimientoclientes;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mantenimientoclientes.bd.ClientesBD;

import java.util.Arrays;

public class Cuestionario extends AppCompatActivity {
    EditText dni;
    EditText apellidos;
    EditText nombre;
    EditText latitud;
    EditText longitud;
    CheckBox vip;

    Button btnGuardar;
    ImageButton btnMapa;
    SpinnerAdapter spa;
    Spinner sp;
    ClientesBD bd;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuestionario);
        btnMapa=(ImageButton) findViewById(R.id.btnMap);
        btnMapa.setOnClickListener(v -> mapa());
        apellidos = (EditText) findViewById(R.id.txtApellidos);
        nombre = (EditText) findViewById(R.id.txtNombre);
        vip = (CheckBox) findViewById(R.id.checkBox);
        latitud = (EditText) findViewById(R.id.txtLatitud);
        longitud = (EditText) findViewById(R.id.txtLongitud);
        bd = new ClientesBD(this);
        sp = (Spinner) findViewById(R.id.spinnerProvincias);
        spa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                Arrays.asList("Pontevedra", "Ourense", "A Coruña", "Lugo"));
        sp.setAdapter(spa);
        dni = (EditText) findViewById(R.id.txtDNI);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(v -> guardarEnBD());
        btnGuardar.setEnabled(false);

        dni.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                String contenido = dni.getText().toString();
                int num;
                try {
                    if (contenido.length() == 9) {
                        num = Integer.parseInt(contenido.substring(0, contenido.length() - 1));
                        if (Character.toUpperCase(contenido.charAt(contenido.length() - 1))
                                == calcularLetraDNI(num)) {
                            Log.i("dni.comprobaciones", "dni correcto");
                            btnGuardar.setEnabled(true);
                            dni.setBackgroundColor(Color.TRANSPARENT);
                        } else {
                            Log.e("dni.comprobaciones", "letra de dni incorrecta");
                            dni.setBackgroundColor(Color.RED);
                            btnGuardar.setEnabled(false);

                        }
                    } else if (contenido.length()==0) {
                        btnGuardar.setEnabled(false);
                        dni.setBackgroundColor(Color.TRANSPARENT);
                    }else{
                        btnGuardar.setEnabled(false);
                    }
                } catch (NumberFormatException ex) {
                    Log.e("dni.comprobaciones", "el numero el dni contiene letras");
                    btnGuardar.setEnabled(false);
                    dni.setBackgroundColor(Color.RED);
                }

            }
        });


    }

    private void mapa() {
        String uri =  "geo:" + latitud + "," + longitud + "?z=17";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "No se encontró Google Maps en tu dispositivo.", Toast.LENGTH_SHORT).show();
        }
    }

    private void guardarEnBD() {
        if (!bd.add(nombre.getText().toString(), apellidos.getText().toString(),
                dni.getText().toString(), sp.getSelectedItem().toString(), vip.isChecked() ? 1 : 0,
                Double.parseDouble(latitud.getText().toString()),
                Double.parseDouble(longitud.getText().toString()))) {
            Toast.makeText(this, "Cliente ya existe, modifique el dni", Toast.LENGTH_SHORT).show();
        } else {
            this.finish();
        }
    }

    public Character calcularLetraDNI(int numeroDNI) {
        int indiceLetra = numeroDNI % 23;
        char[] letras = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X',
                'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K'};
        try {
            return letras[indiceLetra];
        } catch (NumberFormatException ex) {
            Log.e("Cuestionario", "El numero de DNI tiene caracteres que no son numeros");
            return null;
        }
    }
}
