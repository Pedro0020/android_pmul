package com.example.fragmentdadov2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Ranking extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        Intent intent = getIntent();
        ((TextView) findViewById(R.id.datosUltimaPartida)).setText(
                intent.getStringExtra("tiradas") + "tirada, dificultad " +
                        intent.getStringExtra("numeroCaras"));
    }
}
