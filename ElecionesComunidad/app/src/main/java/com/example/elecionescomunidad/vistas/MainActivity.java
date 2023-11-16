package com.example.elecionescomunidad.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.elecionescomunidad.R;
import com.example.elecionescomunidad.bd.UsuariosDB;

public class MainActivity extends AppCompatActivity {
    EditText usr;
    EditText pass;
    UsuariosDB db;
    Button btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new UsuariosDB(this);
        btnEntrar = findViewById(R.id.btnAcceso);
        usr = findViewById(R.id.editTextUsr);
        pass = findViewById(R.id.editTextPassword);
        //BOton de accceso
        btnEntrar.setOnClickListener(v -> permiso());
    }

    /**
     * Verifica que existe el ususario y sino muestra un mensaje
     */
    private void permiso() {
        String usuario=usr.getText().toString();
        Log.i("MainActivity.permiso", "entro en funcionn permiso");
        if (db.validarSesion(usuario, pass.getText().toString())) {
            Log.i("MainActivity.permiso", "usuario y contraseña correctos");
            if (db.haVotado(usuario)) {
                Log.i("MainActivity.permiso", "no ha votado acceso permitido");
               //db.marcarUsuarioComoHaVotado(usuario);
                iniciarVentanaVotacion();
            } else {
                Toast.makeText(this,
                        usuario + " ya ha votado\n acceso dengado", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this,
                    "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
    }


    private void iniciarVentanaVotacion() {
        Intent intent = new Intent(this, VotoActivity.class);
        startActivity(intent);
    }
}
