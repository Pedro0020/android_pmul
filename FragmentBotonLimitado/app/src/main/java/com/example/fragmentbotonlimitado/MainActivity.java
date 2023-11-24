package com.example.fragmentbotonlimitado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fragmentbotonlimitado.fragments.BtnLimitado;
import com.example.fragmentbotonlimitado.interfaces.Click;

public class MainActivity extends AppCompatActivity {

    public static final int NUM_PULSACIONES_TOTALES = 3;
    private EditText usr;
    private EditText pass;
    private CheckBox ch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usr = findViewById(R.id.editTextText);
        pass = findViewById(R.id.editTextTextPassword);
        ch = findViewById(R.id.check);
        // Paso 1: Crear una instancia del fragmento
        BtnLimitado fragment = new BtnLimitado();
        // Paso 2: Iniciar una transacción de fragmento
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Paso 3: Reemplazar o agregar el fragmento en el contenedor
        transaction.replace(R.id.buttonFragment, fragment);
        // Paso 4: Confirmar la transacción
        transaction.commit();
        fragment.eventoClick(new Click() {
            @Override
            public boolean click() {
                boolean est = false;
                if (usr.getText().length() > 0 && pass.getText().length() > 0 && ch.isChecked()) {
                    est = true;
                    Toast.makeText(MainActivity.this, "ClickVAlido", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "click no valido", Toast.LENGTH_SHORT).show();
                }
                return est;
            }

            @Override
            public void ultimoClick() {
                Toast.makeText(MainActivity.this, "Intentos Finalizados", Toast.LENGTH_SHORT).show();
            }
        }, NUM_PULSACIONES_TOTALES);
    }
}