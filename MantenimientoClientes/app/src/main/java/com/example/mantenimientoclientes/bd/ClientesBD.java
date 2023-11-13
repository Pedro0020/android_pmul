package com.example.mantenimientoclientes.bd;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import androidx.annotation.Nullable;

import java.util.ArrayList;

import com.example.mantenimientoclientes.modelos.Cliente;

public class ClientesBD extends SQLiteOpenHelper {

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

    public ClientesBD(@Nullable Context context) {
        super(context, BD_NAME, null, BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        Log.i("Clientesbd.onCreate", BD_NAME + " creating: " + TABLE_CLIENTES);
        try {
            bd.execSQL("CREATE TABLE IF NOT EXISTS "
                    + TABLE_CLIENTES
                    + "( " + CLIENTES_NOMBRE + " TEXT NOT NULL, "
                    + CLIENTES_APELLIDOS + " TEXT NOT NULL, "
                    + CLIENTES_DNI + " TEXT PRIMARY KEY NOT NULL, "
                    + CLIENTES_PROVINCIA + " TEXT NOT NULL, "
                    + CLIENTES_VIP + " INTEGER NOT NULL, "
                    + CLIENTES_LATITUD + " REAL NOT NULL, "
                    + CLIENTES_LONGITUD + " REAL NOT NULL)");
            Log.i("Clientesbd.onCreate2", "paso la ejecucion de la tabla");
            crearClientesIniciales(bd);
        } catch (SQLException ex) {
            Log.e("Clientesbd.onCreate", ex.getMessage());
        }
    }

    // ...

    public boolean add(String nombre, String apellidos, String dni, String provincia,
                    int vip, double latitud, double longitud) {
        boolean est=false;
        Log.i("Clientesbd.add", "añadiendo nuevos datos si no existen");

        SQLiteDatabase bd = this.getWritableDatabase();
        Cursor cursor = null;
        ContentValues values = new ContentValues();

        values.put(CLIENTES_NOMBRE, nombre);
        values.put(CLIENTES_APELLIDOS, apellidos);
        values.put(CLIENTES_DNI, dni);
        values.put(CLIENTES_PROVINCIA, provincia);
        values.put(CLIENTES_VIP, vip);
        values.put(CLIENTES_LATITUD, latitud);
        values.put(CLIENTES_LONGITUD, longitud);
        try {
            bd.beginTransaction();

            cursor = bd.query(TABLE_CLIENTES, new String[]{CLIENTES_DNI},
                    CLIENTES_DNI + " = ?", new String[]{dni},
                    null, null, "1");

            if (cursor.getCount() == 0) {
                est=true;
                bd.insert(TABLE_CLIENTES, null, values);
                bd.setTransactionSuccessful(); // Marcar la transacción como exitosa
            } else {
                Log.e("Clientesbd.add", "datos introducidos ya existen");

            }
        } catch (SQLException ex) {
            Log.e("Clientesbd.onUpgrade", ex.getMessage());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            bd.endTransaction();
        }
        return est;
    }

    // ...

    private void crearClientesIniciales(SQLiteDatabase bd) {
        Log.i("crearClientesIniciales", "HOLA LLEGO AQUI");

        add(bd, "Juan", "Pérez", "12345678A", "Madrid", 1,
                40.4168, -3.7038);
        add(bd, "Maria", "Gomez", "87654321B", "Barcelona",
                0, 41.3851, 2.1734);
        add(bd, "Carlos", "Fernández", "55555555C", "Sevilla",
                1, 37.3886, -5.9822);
        add(bd, "Laura", "Rodriguez", "99999999D", "Valencia",
                0, 39.4699, -0.3763);
    }
    @Override
    public void onUpgrade(SQLiteDatabase bd, int v1, int v2) {
        Log.i("Clientesbd.onUpdate", BD_NAME + " " + v1 + " -> " + v2);

        try {
            bd.beginTransaction();
            bd.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTES);
            bd.setTransactionSuccessful();
        } catch (SQLException ex) {
            Log.e("Clientesbd.onUpgrade", ex.getMessage());
        } finally {
            bd.endTransaction();
        }

        this.onCreate(bd);
    }

    private void add(SQLiteDatabase bd, String nombre, String apellidos, String dni, String provincia,
                     int vip, double latitud, double longitud) {
        ContentValues values = new ContentValues();
        values.put(CLIENTES_NOMBRE, nombre);
        values.put(CLIENTES_APELLIDOS, apellidos);
        values.put(CLIENTES_DNI, dni);
        values.put(CLIENTES_PROVINCIA, provincia);
        values.put(CLIENTES_VIP, vip);
        values.put(CLIENTES_LATITUD, latitud);
        values.put(CLIENTES_LONGITUD, longitud);

        bd.insert(TABLE_CLIENTES, null, values);
    }


    public void update(String nombre, String apellidos, String dni, String provincia,
                       boolean vip, double latitud, double longitud) {
        Log.e("Clientesbd.update", "actualizando datos si existe");

        SQLiteDatabase bd = this.getWritableDatabase();
        Cursor cursor = null;
        ContentValues values = new ContentValues();

        values.put(CLIENTES_NOMBRE, nombre);
        values.put(CLIENTES_APELLIDOS, apellidos);
        values.put(CLIENTES_PROVINCIA, provincia);
        values.put(CLIENTES_VIP, vip);
        values.put(CLIENTES_LATITUD, latitud);
        values.put(CLIENTES_LONGITUD, longitud);

        try {

            bd.beginTransaction();
            cursor = bd.query(TABLE_CLIENTES, new String[]{CLIENTES_DNI},
                    CLIENTES_DNI + " = ?",
                    new String[]{dni}, null, null, "1");

            if (cursor.getCount() > 0) {
                bd.update(TABLE_CLIENTES, values, CLIENTES_DNI + " =?", new String[]{dni});
            } else {
                Log.e("Clientesbd.add", "los datos introducidos no existen crealos para " +
                        "poder actualizarlos");

            }

        } catch (SQLException ex) {
            Log.e("Clientesbd.onUpgrade", ex.getMessage());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            bd.endTransaction();
        }
    }

    public void remove(String dni) {
        SQLiteDatabase bd = this.getWritableDatabase();

        try {
            bd.beginTransaction();
            bd.delete(TABLE_CLIENTES, CLIENTES_DNI + " = ?", new String[]{dni});
            bd.setTransactionSuccessful();
        } catch (SQLException exc) {
            Log.e("Clientesbd.remove", exc.getMessage());
        } finally {
            bd.endTransaction();
        }
    }


    /*    public Cursor getAllClientes() {
            Cursor cursor = null;
            SQLiteDatabase bd = getReadableDatabase();
            try {
                bd.beginTransaction();
                cursor = bd.query(TABLE_CLIENTES, null, null,
                        null, null, null, null, null);
                bd.setTransactionSuccessful();
            } catch (SQLException exc) {
                Log.e("Clientesbd.remove", exc.getMessage());
            } finally {
                bd.endTransaction();
            }
            return cursor;
        }*/
     public ArrayList<Cliente> getAllClientes() {
         Log.i("getAllClientes.inicio", "arrranca metodo");

         Cursor cursor = null;
        ArrayList<Cliente> clientes = new ArrayList<>();
        SQLiteDatabase bd = getReadableDatabase();
        try {
            bd.beginTransaction();
            cursor = bd.query(TABLE_CLIENTES, null, null,
                    null, null, null, null, null);
            while (cursor.moveToNext()) {
                Log.i("GETALLCLIENTS", cursor.getString(0));

                clientes.add(new Cliente(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getInt(4),
                        cursor.getDouble(5), cursor.getDouble(6)));
            }
            bd.setTransactionSuccessful();
        } catch (SQLException exc) {
            Log.e("Clientesbd.Error", exc.getMessage());
        } finally {
            bd.endTransaction();
        }
        return clientes;
    }



}
