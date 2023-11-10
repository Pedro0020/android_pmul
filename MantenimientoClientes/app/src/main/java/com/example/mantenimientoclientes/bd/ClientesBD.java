package com.example.mantenimientoclientes.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Objects;

public class ClientesBD extends SQLiteOpenHelper {

    public static final String DB_NAME = "ClientesDB";
    public static final int DB_VERSION = 1;
    public static final String TABLE_CLIENTES = "Clientes";
    public static final String CLIENTES_NOMBRE = "Nombre";
    public static final String CLIENTES_APELLIDOS = "Apellidos";
    public static final String CLIENTES_DNI = "DNI";
    public static final String CLIENTES_PROVINCIA = "Provincia";
    public static final String CLIENTES_VIP = "VIP";
    public static final String CLIENTES_LATITUD = "Latitud";
    public static final String CLIENTES_LONGITUD = "Longitud";

    public ClientesBD(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //CEREO LA TABLA DE LA BASE DE DATOS CLIENTES
    public void onCreate(SQLiteDatabase bd) {
        Log.i("DBManager", DB_NAME + " creating: " + TABLE_CLIENTES);        //inicio la transacion
        try {
            bd.beginTransaction();
            bd.execSQL("CREATE TABLE IF NOT EXISTS "
                    + TABLE_CLIENTES
                    + "( " + CLIENTES_NOMBRE + " TEXT NOT NULL, "
                    + CLIENTES_APELLIDOS + " TEXT NOT NULL, "
                    + CLIENTES_DNI + " TEXT PRIMARY KEY NOT NULL, "
                    + CLIENTES_PROVINCIA + " TEXT NOT NULL, "
                    + CLIENTES_VIP + " INTEGER NOT NULL, "
                    + CLIENTES_LATITUD + " REAL NOT NULL, "
                    + CLIENTES_LONGITUD + " REAL NOT NULL)");
            bd.setTransactionSuccessful();
        } catch (SQLException ex) {
            //Log.e("DBManager.onCreate", ex.getMessage());
        } finally {
            bd.endTransaction();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int i, int i1) {
        Log.i("DBManager", DB_NAME + " " + v1 + " -> " + v2);

        try {
            bd.beginTransaction();
            bd.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTES);
            bd.setTransactionSuccessful();
        } catch (SQLException ex) {
            //Log.e("DBManager.onUpgrade", ex.getMessage());
        } finally {
            bd.endTransaction();
        }

        this.onCreate(bd);
    }

    public void add(String nombre, String apellidos, String dni, String provincia,
                    boolean vip, double latitud, double longitud) {
        Log.e("DBManager.add", "añadiendo nuevos datos si no existen");

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ContentValues values = new ContentValues();

        values.put(CLIENTES_NOMBRE, nombre);
        values.put(CLIENTES_APELLIDOS, apellidos);
        values.put(CLIENTES_DNI, provincia);
        values.put(CLIENTES_VIP, vip);
        values.put(CLIENTES_LATITUD, latitud);
        values.put(CLIENTES_LONGITUD, longitud);
        try {
            db.beginTransaction();
            cursor = db.query(TABLE_CLIENTES, new String[]{CLIENTES_DNI},
                    CLIENTES_DNI + " = ?", new String[]{dni},
                    null, null, "1");

            if (cursor.getCount() == 0) {
                db.insert(TABLE_CLIENTES, null, values);
            } else {
                Log.e("DBManager.add", "datos introducidos ya existen");
            }
        } catch (SQLException ex) {
            //Log.e("DBManager.onUpgrade", ex.getMessage());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.endTransaction();
        }
    }

    public void update(String nombre, String apellidos, String dni, String provincia,
                       boolean vip, double latitud, double longitud) {
        Log.e("DBManager.update", "actualizando datos si existe");

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ContentValues values = new ContentValues();

        values.put(CLIENTES_NOMBRE, nombre);
        values.put(CLIENTES_APELLIDOS, apellidos);
        values.put(CLIENTES_VIP, vip);
        values.put(CLIENTES_LATITUD, latitud);
        values.put(CLIENTES_LONGITUD, longitud);

        try {

            db.beginTransaction();
            cursor = db.query(TABLE_CLIENTES, new String[]{CLIENTES_DNI},
                    CLIENTES_DNI + " = ?",
                    new String[]{dni}, null, null, "1");

            if (cursor.getCount() > 0) {
                db.update(TABLE_CLIENTES, values, CLIENTES_DNI + " =?", new String[]{dni});
            } else {
                Log.e("DBManager.add", "los datos introducidos no existen crealos para " +
                        "poder actualizarlos");

            }

        } catch (SQLException ex) {
            //Log.e("DBManager.onUpgrade", ex.getMessage());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.endTransaction();
        }
    }
}
