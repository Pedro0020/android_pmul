package com.example.ejerciciossindb.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.ejerciciossindb.R;

public class MainActivity extends AppCompatActivity {

    private Button btnSinBd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.btnSinBd = (Button) findViewById(R.id.btnListaSinBd);
        this.btnSinBd.setOnClickListener(v -> irAppSinBd());
    }

    private void irAppSinBd() {
        Intent intent = new Intent(this, VistaInicialSinBd.class);
        startActivity(intent);
    }
}