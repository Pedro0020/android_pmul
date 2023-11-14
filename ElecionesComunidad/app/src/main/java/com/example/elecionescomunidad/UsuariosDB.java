package com.example.elecionescomunidad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class UsuariosDB extends SQLiteOpenHelper {
    public static final String BD_NAME = "UsuariosBD";
    public static final int BD_VERSION = 1;
    public static final String TABLE_USUARIOS = "usuarios";
    public static final String USUARIOS_NOMBRE = "nombre";
    public static final String USUARIOS_PASSWORD = "contraseña";

    public UsuariosDB(@Nullable Context context) {
        super(context, BD_NAME, null, BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("Usuariosbd.onCreate", BD_NAME + " creating: " + TABLE_USUARIOS);
        try {
            db.beginTransaction();
            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + TABLE_USUARIOS
                    + "( " + USUARIOS_NOMBRE + " TEXT PRIMARY KEY NOT NULL, "
                    + USUARIOS_PASSWORD + " TEXT NOT NULL)");
            Log.i("Usuariosbd.onCreateUsr", "termina de crear la tabla , creo usuarios");
            add(db, "pedro", "123");
            add(db, "ana", "890");
            db.setTransactionSuccessful();
        } catch (SQLException ex) {
            Log.e("Clientesbd.onCreate", ex.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void add(SQLiteDatabase db, String nombre, String password) {
        ContentValues values = new ContentValues();
        values.put(USUARIOS_NOMBRE, nombre);
        values.put(USUARIOS_PASSWORD, password);
        db.insert(TABLE_USUARIOS, null, values);
    }

    //HACER METODO QUE COPRUEBE CONTRASEÑA
    public boolean validarSesion(String usuario, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean est = false;
        Cursor cursor = null;
        try {
            String selection = USUARIOS_NOMBRE + " =? AND " + USUARIOS_PASSWORD + " =?";
            cursor = db.query(TABLE_USUARIOS,
                    new String[]{USUARIOS_NOMBRE}, selection,
                    new String[]{usuario, password}, null, null, null);
            if (cursor.getCount() > 0) {
                est = true;
            } else {
                est = false;
            }
        } catch (SQLException exception) {
            Log.e("productoExisteCarrito", exception.getMessage());
            est = false;
        } finally {
            assert cursor != null;
            cursor.close();
        }
        return est;
    }
}
