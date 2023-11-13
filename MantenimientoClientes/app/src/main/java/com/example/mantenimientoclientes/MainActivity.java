package com.example.mantenimientoclientes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.mantenimientoclientes.bd.ClientesBD;
import com.example.mantenimientoclientes.bd.dbManager;
import com.example.mantenimientoclientes.modelos.Cliente;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayAdapter adaptador;
    ClientesBD bd;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bd = new ClientesBD(this);
        lv = (ListView) findViewById(R.id.lista);
        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,bd.getAllClientes());
        lv.setAdapter(adaptador);
        findViewById(R.id.btnAdd).setOnClickListener(view -> abrirCuestionario());
    }

    @Override
    protected void onResume() {
        super.onResume();
        adaptador.clear();
        adaptador.addAll(bd.getAllClientes());
        adaptador.notifyDataSetChanged();
    }

    private void abrirCuestionario() {
        Intent intent = new Intent(this,Cuestionario.class);
        startActivity(intent);

    }


}