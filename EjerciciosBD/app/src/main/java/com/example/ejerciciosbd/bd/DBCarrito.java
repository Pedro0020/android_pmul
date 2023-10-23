package com.example.ejerciciosbd.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBCarrito extends SQLiteOpenHelper {

    public static String NOMBRE_BD = "productosBD";
    public static int BD_VERSION = 1;

    //CREO LAS CONSTANTES DE LA TABLA
    public static String CARRITO_TABLA_PRODUCTOS = "productos";

    public static String CARRITO_PRODUCTOS_CANTIDAD = "cantidad";
    public static String CARRITO_PRODUCTOS_PRECIO = "precio";
    public static String CARRITO_PRODUCTOS_NOMBRE = "nombre";


    public DBCarrito(@Nullable Context context) {
        super(context, NOMBRE_BD, null, BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DBManager", NOMBRE_BD + " creating: " + CARRITO_TABLA_PRODUCTOS);
        try {


            db.beginTransaction();
            db.execSQL("CREATE TABLE IF NOT EXISTS " + CARRITO_TABLA_PRODUCTOS + "(" +
                    CARRITO_PRODUCTOS_NOMBRE + " text PRIMARY KEY NOT NULL, " +
                    CARRITO_PRODUCTOS_PRECIO + " real NOT NULL, " +
                    CARRITO_PRODUCTOS_CANTIDAD + " integer NOT NULL " +
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
        Log.i("DBManager", NOMBRE_BD + " creating: " + CARRITO_TABLA_PRODUCTOS);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ContentValues values = new ContentValues();

        values.put(CARRITO_PRODUCTOS_NOMBRE, nombre);
        values.put(CARRITO_PRODUCTOS_PRECIO, precio);
        values.put(CARRITO_PRODUCTOS_CANTIDAD, cantidad);

        try {
            db.beginTransaction();
            cursor = db.query(CARRITO_TABLA_PRODUCTOS, new String[]{CARRITO_PRODUCTOS_NOMBRE},
                    CARRITO_PRODUCTOS_NOMBRE, new String[]{nombre},
                    null, null, null, "1"
            );
            if (cursor.getCount() > 0) {
                db.update(CARRITO_TABLA_PRODUCTOS, values,
                        CARRITO_PRODUCTOS_NOMBRE + " = ?", new String[]{nombre});
            } else {
                db.insert(CARRITO_TABLA_PRODUCTOS, null, values);

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
            db.delete(CARRITO_TABLA_PRODUCTOS, CARRITO_PRODUCTOS_NOMBRE + " = ?", new String[]{name});
            db.setTransactionSuccessful();
        } catch (SQLException exc) {
            Log.e("dbRemove", exc.getMessage());
        } finally {
            db.endTransaction();
        }

    }

    public Cursor searchFor(String txt) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor toret = null;

        try {
            toret = db.query(CARRITO_TABLA_PRODUCTOS, null,
                    CARRITO_PRODUCTOS_NOMBRE + " LIKE ?",
                    new String[]{txt}, null, null, null);
        } catch (SQLException exc) {
            Log.e("DBManager.searchFor", exc.getMessage());
        }

        return toret;
    }

    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(CARRITO_TABLA_PRODUCTOS,
                null, null, null, null, null, null);
    }
}
