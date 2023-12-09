package com.example.fragmentdadov2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements FragmentDado.OnFragmentListener {
    private FragmentDado frgDado1, frgDado2;
    private int tiradas;
    private TextView lbTiros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tiradas = 0;
        lbTiros = (TextView) findViewById(R.id.tvTiradas);
        frgDado1 = (FragmentDado) getSupportFragmentManager().findFragmentById(R.id.frgDado1);
        frgDado2 = (FragmentDado) getSupportFragmentManager().findFragmentById(R.id.frgDado2);
        ((Button) findViewById(R.id.btnStart)).setOnClickListener(v -> {
            tiradas = 0;
            lbTiros.setText("0");

            frgDado1.OnFragmentListener(Integer.parseInt(String.valueOf(((EditText) findViewById(R.id.etNumCaras)).getText())), this);
            frgDado2.OnFragmentListener(Integer.parseInt(String.valueOf(((EditText) findViewById(R.id.etNumCaras)).getText())), this);
        });
    }

    @Override
    public boolean onDadoLanzado(FragmentDado frgDado, Integer num) {
        Log.i("etado dado",frgDado1.isEstado()+"");
        Log.i("etado dado",frgDado2.isEstado()+"");
       if(frgDado.equals(frgDado2)&&frgDado1.isEstado()){
           frgDado1.setEstado(false);
           frgDado2.setEstado(false);
           lbTiros.setText(++tiradas+"");
           if(frgDado1.getNum().equals(frgDado2.getNum())){
               frgDado1.deshabilitar();
               frgDado2.deshabilitar();
           }

       }else{
           frgDado2.setEstado(false);
       }

        return false;
    }
}