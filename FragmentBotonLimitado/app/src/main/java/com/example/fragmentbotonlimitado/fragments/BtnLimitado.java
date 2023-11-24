package com.example.fragmentbotonlimitado.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fragmentbotonlimitado.R;
import com.example.fragmentbotonlimitado.interfaces.Click;

public class BtnLimitado extends Fragment {

    private Click cl;
    private int numPulsacionesTotales;
    private int pulsaciones;

    public BtnLimitado() {
        this.numPulsacionesTotales = 0;
        this.pulsaciones = 0;

    }

    public void eventoClick(Click cl, int numPulsacionesTotales) {
        this.numPulsacionesTotales = numPulsacionesTotales;
        this.pulsaciones = 0;
        this.cl = cl;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.vista_fragment, container, false);
        ((Button) vista.findViewById(R.id.btnEntrar)).setOnClickListener(v -> pulsacion());
        return vista;
    }

    private void pulsacion() {
        if (pulsaciones < numPulsacionesTotales) {
            cl.click();
            pulsaciones++;
            Log.i("Btn","click");
        } else if (pulsaciones == numPulsacionesTotales) {

        }
    }


}
