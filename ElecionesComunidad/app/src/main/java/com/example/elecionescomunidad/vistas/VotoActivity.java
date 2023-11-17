package com.example.elecionescomunidad.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.elecionescomunidad.R;
import com.example.elecionescomunidad.bd.EleccionesBD;
import com.example.elecionescomunidad.fragments.FragmentVoto;
import com.example.elecionescomunidad.interfaces.EventoClick;
import com.example.elecionescomunidad.modelos.Candidato;
import com.example.elecionescomunidad.modelos.CandidatoAdapter;

import java.util.ArrayList;

public class VotoActivity extends AppCompatActivity {
    private FragmentVoto fragment;
    ArrayList<Candidato> candidatos;
    ArrayList<Candidato> votados;
    private EleccionesBD elecciones;
    private CandidatoAdapter adp;
    private Spinner sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecion_voto);
        elecciones= new EleccionesBD(this);
        candidatos=elecciones.obtenerCandidatos();
        for(Candidato c:candidatos){
            Toast.makeText(this, c.getNombre(), Toast.LENGTH_SHORT).show();
        }
        adp= new CandidatoAdapter(this,candidatos);
        sp=(Spinner) findViewById(R.id.spinnerEleccion);
        sp.setAdapter(adp);

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
        votados.add((Candidato) sp.getSelectedItem());
    }
}
