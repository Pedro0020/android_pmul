package com.example.fragmentllamadasexamenv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements FragmentTelefono.onListener {

    private int[] ids = {R.id.fgr1, R.id.fgr2, R.id.fgr3, R.id.fgr4};
    private FragmentTelefono[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragments = new FragmentTelefono[ids.length];
        for (int i = 0; i < fragments.length; i++) {
            fragments[i] = (FragmentTelefono) getSupportFragmentManager().findFragmentById(ids[i]);
            fragments[i].setInicio(i + 1 + "", this);
        }

    }

    @Override
    public boolean llamada(FragmentTelefono fragment, String num) {
        for (FragmentTelefono fr : fragments) {
            if (fr.getNumTlfn().equals(num)) {
                if (fr.isLlamadaEnCurso()) {
                    return false;
                } else {
                    fr.recibir(fragment.getNumTlfn(),">");
                }
            }
        }

        return true;
    }

    @Override
    public void colgarLLamada(FragmentTelefono fragment, String num) {
        for (FragmentTelefono fr : fragments) {
            if (fr.getNumTlfn().equals(num)) {
                if (fr.isLlamadaEnCurso()) {
                    fr.colgar();
                }
            }
        }
    }
}