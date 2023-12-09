package com.example.fragmentdadov2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Random;

public class FragmentDado extends Fragment {
    private int numCaras;
    private boolean estado;
    private int numTiros;
    private int numRacha;
    private Button btnTirar;
    private TextView txtNumDado;
    private TextView txtNumTiradas;
    private TextView rachaDado;
    private ArrayList<Integer> registroNums;
    private Spinner spinner;
    private OnFragmentListener listener;


    public interface OnFragmentListener {
        boolean onDadoLanzado(FragmentDado frgDado, Integer num);

    }

    public void OnFragmentListener(int numCaras, OnFragmentListener lis) {
        this.numTiros = 0;
        this.estado = false;
        this.numRacha = 0;
        this.rachaDado.setText("");
        this.txtNumDado.setText("");
        this.txtNumTiradas.setText("");
        listener = lis;
        habilitar();
        setAdapterSpinner(numCaras);
    }

    public void habilitar() {
        btnTirar.setEnabled(true);
        spinner.setEnabled(true);
    }

    public void deshabilitar() {
        btnTirar.setEnabled(false);
        spinner.setEnabled(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragments_dado_con_lineal, container, false);
        this.spinner = (Spinner) view.findViewById(R.id.spnTiroMan);
        this.numCaras = 6;
        estado = false;
        this.btnTirar = (Button) view.findViewById(R.id.btnVoto);
        this.txtNumDado = (TextView) view.findViewById(R.id.numDado);
        this.txtNumTiradas = (TextView) view.findViewById(R.id.numTiradas);
        this.rachaDado = (TextView) view.findViewById(R.id.rachaDado);
        this.registroNums = new ArrayList<>();

        setAdapterSpinner(this.numCaras);

        this.btnTirar.setOnClickListener(v -> {
            if (listener != null) tiro(generarNumAleatorio());
        });

        return view;
    }

    private void setAdapterSpinner(int num) {
        this.numCaras = num;
        spinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, generarContenido()));
        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0 && listener != null) {

                    tiro(Integer.parseInt((String) spinner.getItemAtPosition(position)));
                }
                spinner.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void tiro(int num) {
        this.estado = true;
        this.txtNumDado.setText(num + "");
        this.txtNumTiradas.setText("(" + ++numTiros + ")");
        coprobarRacha(num);
        this.registroNums.add(num);
        listener.onDadoLanzado(this, num);
    }

    private ArrayList<String> generarContenido() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Seleccione");
        for (int i = 1; i <= this.numCaras; i++) {
            list.add(i + "");
        }
        return list;
    }


    private void coprobarRacha(Integer num) {
        if (this.registroNums.size() >= 1 && num.equals(this.registroNums.get(this.registroNums.size() - 1))) {
            this.rachaDado.setText(getText(R.string.text_racha) + " " + (++this.numRacha));
        } else {
            this.numRacha = 0;
            this.rachaDado.setText("");
        }
    }

    private Integer generarNumAleatorio() {
        Random rd = new Random();
        return rd.nextInt(this.numCaras - 1) + 1;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Integer getNum() {
        if (registroNums.size() > 0) {
            return registroNums.get(registroNums.size() - 1);
        } else return null;
    }
}
