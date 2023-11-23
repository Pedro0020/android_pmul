package com.example.fragmentbotonlimitado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.fragmentbotonlimitado.fragments.BtnLimitado;
import com.example.fragmentbotonlimitado.interfaces.Click;

public class MainActivity extends AppCompatActivity {

    public static final int NUM_PULSACIONES_TOTALES = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Paso 1: Crear una instancia del fragmento
        BtnLimitado fragment = new BtnLimitado();
        // Paso 2: Iniciar una transacción de fragmento
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Paso 3: Reemplazar o agregar el fragmento en el contenedor
        transaction.replace(R.id.buttonFragment, fragment);
        // Paso 4: Confirmar la transacción
        transaction.commit();
        fragment.eventoClick(new Click() {
            @Override
            public void click(int num) {

            }

            @Override
            public void ultimoClick() {

            }
        }, NUM_PULSACIONES_TOTALES);
    }
}