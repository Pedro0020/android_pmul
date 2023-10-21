package com.example.ejerciciossindb.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ejerciciossindb.R;
import com.example.ejerciciossindb.model.Item;

import java.util.ArrayList;

public class VistaInicialSinBd extends AppCompatActivity {
    public static final int AÑADIR_ITEM = 100;
    public static final int MODIFICAR_ITEM = 202;
    private Button btnNuevo;
    private ArrayAdapter<Item> adaptadorItems;
    private ArrayList<Item> items;
    private ListView lvLista;
    private TextView numItems;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_layout);
        this.btnNuevo = (Button) findViewById(R.id.btAdd);
        this.lvLista = (ListView) findViewById(R.id.lvItems);

        this.items = new ArrayList<>();
        this.adaptadorItems = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item,items);
        this.lvLista.setAdapter(this.adaptadorItems);

        btnNuevo.setOnClickListener(v -> añadirItems());
        lvLista.setOnItemClickListener((adapterView, view, i, l) -> modificar(i));
        lvLista.setOnItemLongClickListener((adapterView, view, i, l) -> eliminar(i));
    }

    private boolean eliminar (int i) {

        this.items.remove(i);
        this.adaptadorItems.notifyDataSetChanged();
        this.actualizarContador();
        return true;
    }

    private void modificar(int i) {
        Intent subActividad = new Intent(this, ItemEditionActivity.class);
        Item item = this.adaptadorItems.getItem(i);
        Log.i("Nombre Articulo a mod", item.getNombre());
        subActividad.putExtra("nombre", item.getNombre());
        subActividad.putExtra("cantidad", item.getCantidad());
        subActividad.putExtra("precio", item.getPrecio());
        subActividad.putExtra("pos", i);
        Log.d(i + "", "CUANDO MODIFICO: ");

        this.startActivityForResult(subActividad, MODIFICAR_ITEM);
    }

    private void añadirItems() {
        Intent subActividad = new Intent(this, ItemEditionActivity.class);
        subActividad.putExtra("nombre", "");
        subActividad.putExtra("precio", 1.0);
        subActividad.putExtra("cantidad", 1);
        this.startActivityForResult(subActividad, AÑADIR_ITEM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AÑADIR_ITEM && resultCode == Activity.RESULT_OK) {
            Item itm = new Item(data.getExtras().getString("nombre").toString(), data.getExtras().getInt("cantidad"), data.getExtras().getDouble("precio"));
            this.items.add(itm);
            Log.i("mirame",adaptadorItems.toString());
            this.adaptadorItems.notifyDataSetChanged();
            this.actualizarContador();
        } else if (requestCode == MODIFICAR_ITEM && resultCode == Activity.RESULT_OK) {
            Log.d(data.getExtras().getInt("pos") + "", "CUANDO MODIFICO: " + items.size());
            this.items.set(data.getExtras().getInt("pos"), new Item(data.getExtras().getString("nombre").toString(),
                    data.getExtras().getInt("cantidad"),
                    data.getExtras().getDouble("precio")));
            this.adaptadorItems.notifyDataSetChanged();
            this.actualizarContador();


        }

    }

    private void actualizarContador() {
        this.numItems = (TextView) findViewById(R.id.lblNum);
        this.numItems.setText(String.valueOf(this.adaptadorItems.getCount()));
    }


}
