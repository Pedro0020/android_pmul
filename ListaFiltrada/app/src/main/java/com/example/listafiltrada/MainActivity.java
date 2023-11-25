package com.example.listafiltrada;

import androidx.appcompat.app.AppCompatActivity;

import android.database.DataSetObserver;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    public static final String LISTA_DE_FRUTAS = "LISTA DE FRUTAS";
    private EditText filtro;
    private Spinner contenedor;
    private ArrayAdapter adapter;

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
                adapter.addAll(resultadosFiltro(filtro.getText().toString()));
                adapter.notifyDataSetChanged();
                contenedor.setSelection(0);
            }
        });
    }

    private ArrayList<String> resultadosFiltro(String palabra) {
        ArrayList<String> listaOriginal = llenarLista();
        ArrayList<String> listaFiltrada = new ArrayList<>(listaOriginal); // Crear una copia de la lista original

        if (!palabra.isEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                listaFiltrada.removeIf(s -> s.contains(palabra));
            } else {
                Iterator<String> iterator = listaFiltrada.iterator();
                while (iterator.hasNext()) {
                    String s = iterator.next();
                    if (s.contains(palabra)) {
                        iterator.remove();
                    }
                }
            }
        }
        return listaFiltrada;
    }


    private ArrayList<String> llenarLista() {
        String[] frutas = {
                LISTA_DE_FRUTAS,
                "Manzana",
                "Naranja",
                "Banana",
                "Uva",
                "Cereza",
                "Pera",
                "Kiwi",
                "Piña",
                "Mango",
                "Fresa",
                "Sandía",
                "Melón",
                "Ciruela",
                "Coco",
                "Papaya",
                "Aguacate",
                "Albaricoque",
                "Granada"
        };
        return new ArrayList<>(Arrays.asList(frutas));
    }
}