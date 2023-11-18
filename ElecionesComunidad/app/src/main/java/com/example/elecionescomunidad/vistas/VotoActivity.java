package com.example.elecionescomunidad.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
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
    ArrayList<Candidato> candidatos;
    ArrayList<Candidato> votados;
    private EleccionesBD elecciones;
    private CandidatoAdapter adp;
    private Spinner sp;

    private TextView resultados;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecion_voto);
        elecciones = new EleccionesBD(this);
        resultados = (TextView) findViewById(R.id.txtResulados);
        resultados.setVisibility(View.GONE);
        votados = new ArrayList<>();
        candidatos = elecciones.obtenerCandidatos();
        adp = new CandidatoAdapter(this, candidatos);
        sp = (Spinner) findViewById(R.id.spinnerEleccion);
        sp.setAdapter(adp);

        // Paso 1: Crear una instancia del fragmento
        FragmentVoto fragment = new FragmentVoto();
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
        mostrarVotos();

        Intent intent = getIntent();
        setResult(RESULT_OK, intent);

    }

    private void mostrarVotos() {

        StringBuilder sb = new StringBuilder();
        for (Candidato cd : votados) {
            sb.append(cd.getNombre()).append(" 1 voto").append("\n");
            elecciones.addVotoIdCandidato(cd.getId());
        }
        resultados.setVisibility(View.VISIBLE);
        resultados.setText(sb.toString());

    }

    private void voto() {
        Candidato cd = (Candidato) sp.getSelectedItem();
        Toast.makeText(this, "Ha votado a " + cd.getNombre(), Toast.LENGTH_SHORT).show();
        votados.add(cd);
        candidatos.remove(cd);
        adp.notifyDataSetChanged();
    }
}
