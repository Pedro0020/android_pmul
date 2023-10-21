package com.example.ejerciciossindb.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ejerciciossindb.R;

public class ItemEditionActivity extends AppCompatActivity {

    private EditText txtNombre;
    private EditText txtPrecio;
    private EditText txtCantidad;
    private Button btnListo;
    private Button btnLCancelar;
    private Intent datosEnviados;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_edition_item);

        this.txtNombre = (EditText) findViewById(R.id.txtNombre);
        this.txtCantidad = (EditText) findViewById(R.id.txtCantidad);
        this.txtPrecio = (EditText) findViewById(R.id.txtPrecio);
        this.btnListo = (Button) findViewById(R.id.btnTerminarTarea);
        this.btnLCancelar = (Button) findViewById(R.id.btnCancelar);
        datosEnviados = this.getIntent();


        this.txtCantidad.setText(String.valueOf(datosEnviados.getExtras().getInt("cantidad")));
        this.txtNombre.setText(String.valueOf(datosEnviados.getExtras().getString("nombre")));
        this.txtPrecio.setText(String.valueOf(datosEnviados.getExtras().getDouble("precio")));
        this.btnLCancelar.setOnClickListener(this::onCancel);
        btnListo.setOnClickListener(view -> {
            Intent datosRetorno = new Intent();

            datosRetorno.putExtra("nombre", txtNombre.getText().toString());
            datosRetorno.putExtra("cantidad", Integer.parseInt(txtCantidad.getText().toString()));
            datosRetorno.putExtra("precio", Double.parseDouble(txtPrecio.getText().toString()));
            datosRetorno.putExtra("pos", ItemEditionActivity.this.getIntent().getExtras().getInt("pos"));
            ItemEditionActivity.this.setResult(Activity.RESULT_OK, datosRetorno);
            ItemEditionActivity.this.finish();
        });

    }

    private void onCancel(View view) {
        ItemEditionActivity.this.setResult(Activity.RESULT_CANCELED);
        ItemEditionActivity.this.finish();
    }
}
