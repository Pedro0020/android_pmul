package com.example.elecionescomunidad.vistas;

import android.content.Intent;
import android.os.Build;
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
import com.example.elecionescomunidad.bd.UsuariosDB;
import com.example.elecionescomunidad.fragments.FragmentVoto;
import com.example.elecionescomunidad.interfaces.EventoClick;
import com.example.elecionescomunidad.modelos.Candidato;
import com.example.elecionescomunidad.modelos.CandidatoAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class VotoActivity extends AppCompatActivity {
    ArrayList<Candidato> candidatos;
    ArrayList<Candidato> votados;
    private EleccionesBD elecciones;
    private CandidatoAdapter adp;
    private Spinner sp;
    private UsuariosDB us;

    private TextView resultados;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecion_voto);
        elecciones = EleccionesBD.getInstance(this);
        us = UsuariosDB.getInstance(this);
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
        Intent i = getIntent();
        elecciones.addVotosCandidatos(votados);
        //Descomentar esta linea para que bloquee al usuario
        //us.marcarUsuarioComoHaVotado(i.getStringExtra("nombre"));
        mostrarVotos();
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);

    }

    private void mostrarVotos() {
        ArrayList<Candidato> l = elecciones.obtenerCandidatos();
        Collections.sort(l, (o1, o2) -> Integer.compare(o2.getNumVotos(), o1.getNumVotos()));
        //Borro los candidatos que no tengan botos
        //Si es mayor o igual a la versión 24 entra aqui
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            l.removeIf(candidato -> candidato.getNumVotos() == 0);
        } else {
            //sino aquí
            for (int i = 0; i < l.size(); i++) {
                if (l.get(i).getNumVotos() == 0) {
                    l.remove(i);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (Candidato cd : l) {
            sb.append(cd.getNombre()).append(" ").append(cd.getNumVotos()).append(
                    " votos").append("\n");

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
