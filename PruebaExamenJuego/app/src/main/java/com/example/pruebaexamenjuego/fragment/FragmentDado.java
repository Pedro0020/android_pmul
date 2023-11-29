package com.example.pruebaexamenjuego.fragment;

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

import com.example.pruebaexamenjuego.R;

import java.util.ArrayList;
import java.util.Random;

public class FragmentDado extends Fragment {
    public static final int NUM_CARAS_DADO = 6;
    private ArrayList<String> acumulados;
    private Spinner spinnerTiroManual;
    private Button btnTirarDado;
    private TextView txtRacha;
    private int numRacha;

    //    private int ultimoNum;
    public FragmentDado() {
        numRacha = 0;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_fragment_dado, container, false);
        btnTirarDado = (Button) view.findViewById(R.id.btnTirar);
        spinnerTiroManual = (Spinner) view.findViewById(R.id.spinnerSelecionManual);
        //NO FUNCIONA NO SE PORQUÉ
//        spinnerTiroManual.setPrompt(getString(R.string.promptSpinner));
        //
        btnTirarDado.setOnClickListener(v -> tiroDado());
        prepararSpinner();
        acumulados = new ArrayList<>();
        txtRacha = view.findViewById(R.id.txtRacha);
        txtRacha.setText(getString(R.string.racha) + numRacha);
        spinnerTiroManual.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    spinnerTiroManual.setSelection(0);
                    tiroDado((String) spinnerTiroManual.getItemAtPosition(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    private void prepararSpinner() {
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1);
        //NO ES UN INT PORQUE ASI PUEDO TENER UN TEXTO DE PRIMERAS Y LO FUERZO SIEMPRE A ESTAR FIJO EN
        //ESA POSICION PORQUE SINO VA A IR MAL EL ONITEMSELECTED PORQUE SI NO CAMBIA DE POSICION
        //NO SE ACTIVA EL
        //posible mejora
        ArrayList<String> nums = new ArrayList<>();
        nums.add(getString(R.string.dafault_spinner));
        for (int i = 1; i <= NUM_CARAS_DADO; i++) {
            nums.add(i + "");
        }
        adapterSpinner.addAll(nums);
        spinnerTiroManual.setAdapter(adapterSpinner);

    }

    public Integer tiroDado() {
        int num = generarNumAleatorio();
        acumulados.add(num + "");
        btnTirarDado.setText(num + "");
        comprobarRacha();
        return num;
    }

    //ESTE LO DEJO EN PUBLICO PARA HACER YO UNAS COMPRIOBACIONES POR CODIGO
    //POR SEGURIDAD DEBRIA PONER RESULT COMO UN INT O CONVERTIRLO A NUM PARA QUE SALTE
    //UNA EXCEPCION Y CONTROLARLA
    //posible mejora
    public Integer tiroDado(String result) {
        acumulados.add(result);
        btnTirarDado.setText(result + "");
        comprobarRacha();
        return Integer.parseInt(result);
    }

    private void comprobarRacha() {
        //TAL Y COMO ESTOY UTILIZANDO LA LISTA DEBERÍA USAR UN ARRAY
        //SOLO NECESITO DOS POSICIONES
        try {
            String num1 = acumulados.get(acumulados.size() - 2);
            Log.i("entro en comprobarRacha", "num1 " + num1 + "");
            String num2 = acumulados.get(acumulados.size() - 1);
            Log.i("entro en comprobarRacha", "num2 " + num2 + "");
            if (num2.equals(num1)) {
                numRacha++;
                Log.i("entro en comprobarRacha", "son iguales");
            } else {
                Log.i("entro en comprobarRacha", "no son iguales son");
                numRacha = 0;
                acumulados.clear();
                acumulados.add(num2);
            }
        } catch (IndexOutOfBoundsException ex) {
            Log.i("IndexOutException", acumulados.get(acumulados.size() - 1) + "");
        }
        txtRacha.setText(getString(R.string.racha) + numRacha);

    }

    private int generarNumAleatorio() {
        Random rd = new Random();
        return 1 + rd.nextInt(NUM_CARAS_DADO);
    }

}
