package com.example.fragmentdadov2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements FragmentDado.OnFragmentListener {
    private FragmentDado frgDado1, frgDado2;
    private int tiradas, num;
    private TextView lbTiros;
    private Button btnFinalizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.btnFinalizar = (Button) findViewById(R.id.btnFinPartida);
        this.btnFinalizar.setEnabled(false);
        this.tiradas = 0;
        this.lbTiros = (TextView) findViewById(R.id.tvTiradas);
        this.frgDado1 = (FragmentDado) getSupportFragmentManager().findFragmentById(R.id.frgDado1);
        this.frgDado2 = (FragmentDado) getSupportFragmentManager().findFragmentById(R.id.frgDado2);
        ((Button) findViewById(R.id.btnStart)).setOnClickListener(v -> {
            try {
                this.btnFinalizar.setEnabled(false);
                num = Integer.parseInt(((EditText) findViewById(R.id.etNumCaras)).getText().toString());
                this.tiradas = 0;
                this.lbTiros.setText("0");
                this.frgDado1.OnFragmentListener(num, this);
                this.frgDado2.OnFragmentListener(Integer.parseInt(String.valueOf(((EditText) findViewById(R.id.etNumCaras)).getText())), this);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Introduce un número de caras válido", Toast.LENGTH_SHORT).show();
            }

        });
        btnFinalizar.setOnClickListener(v -> guardarPartida());
    }

    private void guardarPartida() {
        Intent intent = new Intent(this, Ranking.class);
        intent.putExtra("numeroCaras", num);
        intent.putExtra("tiradas", tiradas);
        intent.putExtra("tiradas", tiradas);
        startActivity(intent);
    }


    @Override
    public void onDadoLanzado(FragmentDado frgDado, Integer num) {
        if (frgDado.equals(this.frgDado2) && this.frgDado1.isEstado()) {
            this.frgDado1.setEstado(false);
            this.frgDado2.setEstado(false);
            this.lbTiros.setText(++tiradas + "");
            if (this.frgDado1.getNum().equals(this.frgDado2.getNum())) {
                this.frgDado1.deshabilitar();
                this.frgDado2.deshabilitar();
                this.btnFinalizar.setEnabled(true);
            }

        } else if (frgDado.equals(this.frgDado1)) {
        } else {
            Toast.makeText(this, "Lanza los dados por orden descendente", Toast.LENGTH_SHORT).show();
            this.frgDado2.setEstado(false);
        }
    }
}