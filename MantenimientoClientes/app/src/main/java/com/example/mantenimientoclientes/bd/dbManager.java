package com.example.mantenimientoclientes.bd;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mantenimientoclientes.modelos.Cliente;

import java.util.ArrayList;

public class dbManager extends SQLiteOpenHelper {

        public static final String BD_NAME = "ClientesBD";
        public static final int BD_VERSION = 2;
        public static final String TABLE_CLIENTES = "Clientes";
        public static final String CLIENTES_NOMBRE = "Nombre";
        public static final String CLIENTES_APELLIDOS = "Apellidos";
        public static final String CLIENTES_DNI = "DNI";
        public static final String CLIENTES_PROVINCIA = "Provincia";
        public static final String CLIENTES_VIP = "VIP";
        public static final String CLIENTES_LATITUD = "Latitud";
        public static final String CLIENTES_LONGITUD = "Longitud";

        public dbManager(Context context) {
            super(context, BD_NAME, null, BD_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createTableQuery = "CREATE TABLE " + TABLE_CLIENTES + "(" +
                    CLIENTES_NOMBRE + " TEXT," +
                    CLIENTES_APELLIDOS + " TEXT," +
                    CLIENTES_DNI + " TEXT PRIMARY KEY," +
                    CLIENTES_PROVINCIA + " TEXT," +
                    CLIENTES_VIP + " INTEGER," +
                    CLIENTES_LATITUD + " REAL," +
                    CLIENTES_LONGITUD + " REAL" +
                    ")";
            db.execSQL(createTableQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Puedes implementar acciones específicas si la estructura de la base de datos cambia.
        }

        public void addCliente(Cliente cliente) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CLIENTES_NOMBRE, cliente.getNombre());
            values.put(CLIENTES_APELLIDOS, cliente.getApellidos());
            values.put(CLIENTES_DNI, cliente.getDni());
            values.put(CLIENTES_PROVINCIA, cliente.getProvincia());
            values.put(CLIENTES_VIP, cliente.getVip());
            values.put(CLIENTES_LATITUD, cliente.getLatitud());
            values.put(CLIENTES_LONGITUD, cliente.getLongitud());

            db.insert(TABLE_CLIENTES, null, values);
            db.close();
        }

    public ArrayList<Cliente> getAllClientes() {
        Log.i("getAllClientes", "entro");
        ArrayList<Cliente> clientesList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_CLIENTES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        int nombreIndex = cursor.getColumnIndex(CLIENTES_NOMBRE);
        int apellidosIndex = cursor.getColumnIndex(CLIENTES_APELLIDOS);
        int dniIndex = cursor.getColumnIndex(CLIENTES_DNI);
        int provinciaIndex = cursor.getColumnIndex(CLIENTES_PROVINCIA);
        int vipIndex = cursor.getColumnIndex(CLIENTES_VIP);
        int latitudIndex = cursor.getColumnIndex(CLIENTES_LATITUD);
        int longitudIndex = cursor.getColumnIndex(CLIENTES_LONGITUD);

        if (cursor.moveToFirst()) {
            do {
                Log.i("getAllClientes", "estoy dentro del bulce");

                String nombre = cursor.getString(nombreIndex);
                String apellidos = cursor.getString(apellidosIndex);
                String dni = cursor.getString(dniIndex);
                String provincia = cursor.getString(provinciaIndex);
                int vip = cursor.getInt(vipIndex);
                double latitud = cursor.getDouble(latitudIndex);
                double longitud = cursor.getDouble(longitudIndex);

                Cliente cliente = new Cliente(nombre, apellidos, dni, provincia, vip, latitud, longitud);
                clientesList.add(cliente);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return clientesList;
    }


}
