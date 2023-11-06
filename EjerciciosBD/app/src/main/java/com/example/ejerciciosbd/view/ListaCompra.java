package com.example.ejerciciosbd.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.ejerciciosbd.R;
import com.example.ejerciciosbd.bd.DBAlmacen;
import com.example.ejerciciosbd.bd.DBCarrito;
import com.example.ejerciciosbd.model.Item;

import java.util.ArrayList;

public class
ListaCompra extends AppCompatActivity {
    public static final int AÑADIR_ITEM = 100;
    public static final int MODIFICAR_ITEM = 202;
    private Button btnNuevo;
    private ListView lvLista;
    private TextView numItems;
    private EditText numProds;

    private DBCarrito gestorDBCarro;
    private SimpleCursorAdapter adaptadorDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        this.gestorDBCarro = new DBCarrito(this);
        this.btnNuevo = (Button) findViewById(R.id.btAdd);
        this.lvLista = (ListView) findViewById(R.id.lvItems);
        this.numProds = (EditText) findViewById(R.id.lvLista_Item_Cantidad);

        this.btnNuevo.setOnClickListener(v -> añadirItemDB());
        this.adaptadorDB = new SimpleCursorAdapter(this, android.R.layout.simple_selectable_list_item, null, new String[]{DBCarrito.CARRITO_PRODUCTOS_NOMBRE, DBCarrito.CARRITO_PRODUCTOS_CANTIDAD, DBCarrito.CARRITO_PRODUCTOS_PRECIO},
                new int[]{R.id.lvLista_Item_Nombre, R.id.lvLista_Item_Cantidad, R.id.lvLista_Item_Precio});
        this.lvLista.setAdapter(this.adaptadorDB);
        this.numProds.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //gestorDBCarro.add();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.adaptadorDB = new SimpleCursorAdapter(this, android.R.layout.simple_selectable_list_item, null, new String[]{DBCarrito.CARRITO_PRODUCTOS_NOMBRE, DBCarrito.CARRITO_PRODUCTOS_CANTIDAD, DBCarrito.CARRITO_PRODUCTOS_PRECIO},
                new int[]{R.id.lvLista_Item_Nombre, R.id.lvLista_Item_Cantidad, R.id.lvLista_Item_Precio});
        this.lvLista.setAdapter(this.adaptadorDB);
    }

    private void añadirItemDB() {
        Intent subActividad = new Intent(this,SelecionItem.class);
        subActividad.putExtra("nombre", "");
        subActividad.putExtra("cantidad", 0);
        subActividad.putExtra("precio", 0.0);
        this.startActivityForResult(subActividad, AÑADIR_ITEM);
    }
}