package com.example.fragmentllamadasexamenv2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentTelefono extends Fragment {

    private TextView lbNum;
    private EditText txtMarcacion;
    private ImageButton imagen;
    private boolean llamadaEnCurso;
    private String numTlfn;
    private String numTlfnLLamada;

    public onListener listener;

    interface onListener {
        boolean llamada(FragmentTelefono fragment, String num);

        void colgarLLamada(FragmentTelefono fragment, String num);
    }


    public void setInicio(String num, onListener lis) {
        numTlfn = num;
        numTlfnLLamada = "0";
        listener = lis;
    }

    public String getNumTlfn() {
        return numTlfn;
    }

    public boolean isLlamadaEnCurso() {
        return llamadaEnCurso;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_telefono, container, false);
        lbNum = (TextView) view.findViewById(R.id.numTlfn);
        lbNum.setText(numTlfn);
        imagen = (ImageButton) view.findViewById(R.id.imageButton);
        txtMarcacion = (EditText) view.findViewById(R.id.marcacionTlfn);
        llamadaEnCurso = false;

        imagen.setOnClickListener(v -> {
            if (!txtMarcacion.getText().toString().isEmpty()) {
                accionLLamada();
            } else {
                Toast.makeText(getContext(), "Introduzca un número", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void accionLLamada() {
        numTlfnLLamada = txtMarcacion.getText().toString();
        if (numTlfnLLamada.equals(numTlfn)) {
            Toast.makeText(getContext(), "No puedes llamar a tu mismo número", Toast.LENGTH_SHORT).show();
            return;
        }
        if (llamadaEnCurso) {
            listener.colgarLLamada(this, numTlfnLLamada);
            colgar();
        } else if (listener.llamada(this, numTlfnLLamada)) {
            recibir(numTlfnLLamada, "<");
        }
    }

    public void colgar() {
        llamadaEnCurso = false;
        txtMarcacion.setEnabled(true);
        txtMarcacion.setText("");
        imagen.setImageResource(android.R.drawable.ic_menu_call);
        lbNum.setText(numTlfn);
    }

    public void recibir(String numEntrante, String estado) {
        llamadaEnCurso = true;
        txtMarcacion.setText(numEntrante);
        lbNum.setText(numTlfn + " " + estado + " " + numEntrante);
        txtMarcacion.setEnabled(false);
        imagen.setImageResource(android.R.drawable.sym_call_incoming);
    }

}
