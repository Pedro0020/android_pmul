package com.example.listafiltrada;

import androidx.appcompat.app.AppCompatActivity;

import android.database.DataSetObserver;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    public static final String LISTA_DE_FRUTAS = "LISTA DE FRUTAS";
    private EditText filtro;
    private Spinner contenedor;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        filtro = findViewById(R.id.editFiltro);
        contenedor = findViewById(R.id.spinner);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, llenarLista());
        contenedor.setAdapter(adapter);

        filtro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.clear();
                adapter.addAll(resultadosFiltro(s.toString()));
                adapter.notifyDataSetChanged();
                contenedor.setSelection(0);

            }
        });
    }


    private ArrayList<String> resultadosFiltro(String palabra) {
        ArrayList<String> lista = llenarLista();

        if (!palabra.isEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Log.i("RMEOVE IF", palabra);
                lista.removeIf(s -> !s.contains(palabra));
                if (!lista.contains(LISTA_DE_FRUTAS)) {
             lista.add(0, LISTA_DE_FRUTAS);
                }
            }
        } else {
        Log.i("No RMEOVE IF", palabra);
        Iterator<String> iterator = lista.iterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
            if (!s.contains(palabra)) {
                iterator.remove();
            }
        }
        if (!lista.contains(LISTA_DE_FRUTAS)) {
            lista.add(0, LISTA_DE_FRUTAS);
        }
        }
        return lista;
    }


    private ArrayList<String> llenarLista() {
        String[] frutas = {LISTA_DE_FRUTAS, "Manzana",
                "Manzana Golden", "Naranja", "Banana", "Uva", "Cereza", "Pera",
                "Kiwi", "Piña", "Mango", "Fresa", "Sandía", "Melón", "Ciruela",
                "Coco", "Papaya", "Aguacate", "Albaricoque", "Granada"};
        return new ArrayList<>(Arrays.asList(frutas));
    }
}