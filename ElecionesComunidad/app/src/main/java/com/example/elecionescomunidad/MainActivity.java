package com.example.elecionescomunidad;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText usr;
    EditText pass;
    UsuariosDB db;
    Button btnEntrar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db= new UsuariosDB(this);
        btnEntrar= (Button) findViewById(R.id.btnInicio);
        usr = (EditText) findViewById(R.id.editTextUsr);
        pass = (EditText) findViewById(R.id.editTextPassword);
        setContentView(R.layout.activity_main);
        if(usr.getText().length()>0&&pass.getText().length()>0){
            btnEntrar.setEnabled(true);
        }else{
            btnEntrar.setEnabled(false);
        }

        btnEntrar.setOnClickListener(v -> permiso();
    }

    private void permiso() {
        if(db.validarSesion(usr.getText().toString(),pass.getText().toString())){
            Intent intent = new Intent();
        }
    }
}