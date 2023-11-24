package com.example.elecionescomunidad.bd;

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
    public static final String USUARIOS_HA_VOTADO = "ha_votado";
    private static UsuariosDB instancia;

    private UsuariosDB(@Nullable Context context) {
        super(context, BD_NAME, null, BD_VERSION);
    }

    public static UsuariosDB getInstance(Context context) {
        if (instancia == null) {
            instancia = new UsuariosDB(context);
        }
        return instancia;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("Usuariosbd.onCreate", BD_NAME + " creating: " + TABLE_USUARIOS);
        try {
            db.beginTransaction();
            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + TABLE_USUARIOS
                    + "( " + USUARIOS_NOMBRE + " TEXT PRIMARY KEY NOT NULL, "
                    + USUARIOS_PASSWORD + " TEXT NOT NULL, " +
                    USUARIOS_HA_VOTADO + " INTEGER NOT NULL )");
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
        //DEBERIA COMPROBAR QUE NO EXISTE NADIE CON EL MISMO ID PERo no LO TENGO ENCUENTA PORQUE
        //EN MI APP NO SE CREAN USUARIOS PERO DEBERIA HACERLO DE TODOS MODOS
        ContentValues values = new ContentValues();
        values.put(USUARIOS_NOMBRE, nombre);
        values.put(USUARIOS_PASSWORD, password);
        values.put(USUARIOS_HA_VOTADO, 0);
        db.insert(TABLE_USUARIOS, null, values);
    }

    //METODO QUE COMPRUEBE SI PUEDE ACCEDER O NO
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

    public boolean haVotado(String usuario) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean haVotado = false;
        Cursor cursor = null;

        try {
            String selection = USUARIOS_NOMBRE + " =? AND " + USUARIOS_HA_VOTADO + " =?";
            cursor = db.query(TABLE_USUARIOS,
                    new String[]{USUARIOS_NOMBRE},
                    selection,
                    new String[]{usuario, "0"},
                    null, null, null);

            haVotado = cursor.getCount() > 0;
        } catch (SQLException exception) {
            Log.e("haVotado", exception.getMessage());
            haVotado = false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return haVotado;
    }

    public void marcarUsuarioComoHaVotado(String usuario) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(USUARIOS_HA_VOTADO, 1);
            db.update(TABLE_USUARIOS, values, USUARIOS_NOMBRE + "=?", new String[]{usuario});
        } catch (SQLException exception) {
            Log.e("voto." + usuario, exception.getMessage());
        } finally {
            db.close();  // Cierra la base de datos
        }
    }
}
