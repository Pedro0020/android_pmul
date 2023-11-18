package com.example.elecionescomunidad.vistas;

import androidx.annotation.Nullable;
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
    private static final int CODIGO_INICIO_SESION = 100;
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
        String usuario = usr.getText().toString();
        Log.i("MainActivity.permiso", "entro en funcionn permiso");
        if (db.validarSesion(usuario, pass.getText().toString())) {
            Log.i("MainActivity.permiso", "usuario y contraseña correctos");
            if (db.haVotado(usuario)) {
                Log.i("MainActivity.permiso", "no ha votado, acceso permitido");
                iniciarVentanaVotacion();
            } else {
                Toast.makeText(this,
                        usuario + " ya ha votado,\n acceso dengado", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this,
                    "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODIGO_INICIO_SESION) {
            if (RESULT_OK == resultCode) {
                Toast.makeText(this, "Recuerde, cada voto cuenta" +
                        " gracias por votar", Toast.LENGTH_SHORT).show();
                db.marcarUsuarioComoHaVotado(data.getStringExtra("nombre"));
            } else {
                Toast.makeText(this, "Ha salido de la votacion" +
                        " antes de haber terminado", Toast.LENGTH_SHORT).show();

            }
        }
    }


    private void iniciarVentanaVotacion() {
        Intent intent = new Intent(this, VotoActivity.class);
        intent.putExtra("nombre", usr.getText().toString());
        startActivityForResult(intent, CODIGO_INICIO_SESION);
    }
}
