package com.example.saludar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button saludar = findViewById(R.id.saludo);
        saludar.setOnClickListener(view -> escribir());
    }

    private void escribir() {
        TextView respuesta = this.findViewById(R.id.respuesta);
        EditText cajon = this.findViewById(R.id.c1);
        respuesta.setText(cajon.getText().toString());
    }




}