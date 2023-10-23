package com.example.ejerciciosbd.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBAlmacen extends SQLiteOpenHelper {

    public static String NOMBRE_BD = "productosBD";
    public static int BD_VERSION = 1;

    //CREO LAS COSNTANTES DE LA TABLA
    public static String TABLA_PRODUCTOS = "productos";

    //public static String PRODUCTOS_CANTIDAD = "cantidad";
    public static String PRODUCTOS_PRECIO = "precio";
    public static String PRODUCTOS_NOMBRE = "nombre";
    public static String PRODUCTOS_STOCK_ACTUAL = "stockActual";
    public static String PRODUCTOS_STOCK_MIN = "stockMin";

    private DBAlmacen(@Nullable Context context) {
        super(context, NOMBRE_BD, null, BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DBManager", NOMBRE_BD + " creating: " + TABLA_PRODUCTOS);
        try {


            db.beginTransaction();
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLA_PRODUCTOS + "(" +
                    PRODUCTOS_NOMBRE + " text PRIMARY KEY NOT NULL, " +
                    PRODUCTOS_PRECIO + " real NOT NULL, " +
                    //PRODUCTOS_CANTIDAD + " integer NOT NULL, " +
                    PRODUCTOS_STOCK_ACTUAL + " integer NOT NULL, " +
                    PRODUCTOS_STOCK_MIN + " integer NOT NULL " +
                    ")");
            db.setTransactionSuccessful();
        } catch (SQLException ex) {
            Log.e("DBManager.onCreate", ex.getMessage());
        } finally {
            db.close();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("DBManager", NOMBRE_BD + " " + oldVersion + " -> " + newVersion);

        try {
            db.beginTransaction();
            db.execSQL("DROP TABLE IF EXISTS " + NOMBRE_BD);
            db.setTransactionSuccessful();
        } catch (SQLException ex) {
            Log.e("DBManager.onUpgrade", ex.getMessage());
        } finally {
            db.endTransaction();
        }
        this.onCreate(db);
    }


    public void add(String nombre, double precio, String cantidad) {
        Log.i("DBManager", NOMBRE_BD + " creating: " + TABLA_PRODUCTOS);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ContentValues values = new ContentValues();

        values.put(PRODUCTOS_NOMBRE, nombre);
        values.put(PRODUCTOS_PRECIO, precio);
        //values.put(PRODUCTOS_CANTIDAD, cantidad);

        try {
            db.beginTransaction();
            cursor = db.query(TABLA_PRODUCTOS, new String[]{PRODUCTOS_NOMBRE},
                    PRODUCTOS_NOMBRE, new String[]{nombre},
                    null, null, null, "1"
            );
            if (cursor.getCount() > 0) {
                db.update(TABLA_PRODUCTOS, values,
                        PRODUCTOS_NOMBRE + " = ?", new String[]{nombre});
            } else {
                db.insert(TABLA_PRODUCTOS, null, values);

            }
            db.setTransactionSuccessful();
        } catch (SQLException ex) {


        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.endTransaction();
        }

    }

    public void remove(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();
            db.delete(TABLA_PRODUCTOS, PRODUCTOS_NOMBRE + " = ?", new String[]{name});
            db.setTransactionSuccessful();
        } catch (SQLException exc) {
            Log.e("dbRemove", exc.getMessage());
        } finally {
            db.endTransaction();
        }

    }
    public Cursor searchFor (String txt)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor toret = null;

        try {
            toret = db.query(TABLA_PRODUCTOS, null,
                    PRODUCTOS_NOMBRE + " LIKE ?",
                    new String[]{txt}, null, null, null);
        } catch (SQLException exc) {
            Log.e("DBManager.searchFor", exc.getMessage());
        }

        return toret;
    }

    public Cursor getAllContacts ()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(TABLA_PRODUCTOS,
                null, null, null, null, null, null);
    }
}
