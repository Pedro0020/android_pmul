package com.example.fragmentbotonlimitado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fragmentbotonlimitado.bd.UsuariosDB;
import com.example.fragmentbotonlimitado.fragments.BtnLimitado;
import com.example.fragmentbotonlimitado.interfaces.Click;

public class MainActivity extends AppCompatActivity {

    public static final int NUM_PULSACIONES_TOTALES = 3;
    private EditText usr;
    private EditText pass;
    private CheckBox ch;
    private UsuariosDB bdUsr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usr = findViewById(R.id.editTextText);
        bdUsr = UsuariosDB.getInstance(this);
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
        if (ch.isChecked() && usr.getText().length() > 0 && usr.getText().length() > 0) {
        }
        fragment.eventoClick(new Click() {
            @Override

            public boolean click() {
                boolean est = false;
                if (usr.getText().length() > 0 && pass.getText().length() > 0 && ch.isChecked()) {
                    est = true;
                    String mss= bdUsr.validarSesion(usr.getText().toString(), pass.getText().toString())
                            ?"Usuario Correcto":"Usuario incorrecto";

                    Toast.makeText(MainActivity.this, mss, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Completa todos los campos",
                            Toast.LENGTH_SHORT).show();
                }
                return est;
            }

            @Override
            public void ultimoClick() {
                Toast.makeText(MainActivity.this, "Número de intentos superados", Toast.LENGTH_SHORT).show();
            }
        }, NUM_PULSACIONES_TOTALES);
    }

    private void iniciarSesion() {
        Toast.makeText(this, "Inicio", Toast.LENGTH_SHORT).show();
    }
}