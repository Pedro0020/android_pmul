package com.example.pruebaexamenjuego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.pruebaexamenjuego.fragment.FragmentDado;

public class MainActivity extends AppCompatActivity {
    private Button btnTirar;
    public static final int NUM_DADOS = 2;
    private final FragmentDado[] dados = new FragmentDado[NUM_DADOS];
    private int intentos;
    private final Integer[] nums = new Integer[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentos = 0;
        btnTirar = (Button) findViewById(R.id.btnTodos);

        for (int i = 0; i < NUM_DADOS; i++) {
            dados[i] = new FragmentDado();
            agregarFragment(dados[i], "Dado " + i, R.id.dado1);
        }

        btnTirar.setOnClickListener(v -> lanzarDados());


    }

    private void lanzarDados() {
//        if (dados.equals(dado2.tiroDado())) {
//
//        } else {
//            intentos++;
//            ((TextView) findViewById(R.id.txtLanzamientosEfect)).setText(getString(
//                    R.string.frase_lanzamientos) + intentos);
//        }

    }

    public void agregarFragment(Fragment fr, String nombre, int layout) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(layout, fr, nombre);
        transaction.commit();


    }
}