package com.example.elecionescomunidad.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.elecionescomunidad.R;
import com.example.elecionescomunidad.interfaces.EventoClick;

public class FragmentVoto extends Fragment {
    public static final int NUM_MAX_VOTACIONES = 3;
    private int numIntentos;
    private Button btnVoto;
    EventoClick listener;

    public FragmentVoto() {
    }

    public void setOnClickListener(EventoClick listener){
        this.listener=listener;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_boton_voto, container, false);
        numIntentos = 0;
        btnVoto = (Button) view.findViewById(R.id.btnVoto);
        btnVoto.setOnClickListener(v -> funcionalidadBoton());
        return view;
    }

    private void funcionalidadBoton() {
        if (numIntentos <= 3) {
            numIntentos++;
            listener.onClick();
        }
        switch (numIntentos) {
            case 1:
                btnVoto.setText(R.string.texto_voto_uno);
                break;
            case 2:
                btnVoto.setText(R.string.texto_voto_dos);
                break;
            case NUM_MAX_VOTACIONES:
                btnVoto.setText(R.string.texto_voto_tres);
                listener.ultimoClick();
                break;
            default:
                Toast.makeText(requireContext(),
                        "No se permite votar mas veces", Toast.LENGTH_SHORT).show();
        }

    }
}
