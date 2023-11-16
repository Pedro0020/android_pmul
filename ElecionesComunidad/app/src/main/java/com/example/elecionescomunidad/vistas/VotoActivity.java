package com.example.elecionescomunidad.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.elecionescomunidad.R;
import com.example.elecionescomunidad.fragments.FragmentVoto;
import com.example.elecionescomunidad.interfaces.EventoClick;
import com.example.elecionescomunidad.modelos.Candidato;

import java.util.ArrayList;

public class VotoActivity extends AppCompatActivity {
    private FragmentVoto fragment;
    ArrayList<Candidato> candidatos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecion_voto);

        // Paso 1: Crear una instancia del fragmento
        fragment = new FragmentVoto();

        // Paso 2: Iniciar una transacción de fragmento
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Paso 3: Reemplazar o agregar el fragmento en el contenedor
        transaction.replace(R.id.fragmentContainer, fragment);

        // Paso 4: Confirmar la transacción
        transaction.commit();

        fragment.setOnClickListener(new EventoClick() {
            @Override
            public void onClick() {
                voto();
            }

            @Override
            public void ultimoClick() {
                transferirVotosBD();
            }
        });

    }

    private void transferirVotosBD() {
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
    }

    private void voto() {
    }
}
