package com.example.ajustessharedpreferences;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
public class AjustesActivity extends AppCompatActivity {
    Ajustes ajustes;
    EditText etNombre,etCorreo,etEdad;
    CheckBox cbRecibirPublicidad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        etNombre=findViewById(R.id.nombre);
        etCorreo=findViewById(R.id.correo);
        etEdad=findViewById(R.id.edad);
        cbRecibirPublicidad=findViewById(R.id.recibirPublicidad);
        findViewById(R.id.bGuardar).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { Guardar(); }
        });

        ajustes = Ajustes.getInstance(this);
        etNombre.setText(ajustes.getNombre());
        etCorreo.setText(ajustes.getCorreo());
        int edad=ajustes.getEdad(-1);
        etEdad.setText(edad==-1?"":edad+"");
        cbRecibirPublicidad.setChecked(ajustes.getRecibirPublicidad());
    }
    private void Guardar() {
        String nombre=etNombre.getText().toString();
        String correo=etCorreo.getText().toString();
        String edad=etEdad.getText().toString();
        boolean recibirPublicidad=cbRecibirPublicidad.isChecked();
        if(ajustes.set(nombre,correo,edad,recibirPublicidad))
            finish();
        else
            Toast.makeText(this,getString(R.string.errorGuardarAjustes),Toast.LENGTH_LONG).show();
    }
}
