package com.example.elecionescomunidad.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class EleccionesBD extends SQLiteOpenHelper {
    public static final String BD_NAME = "EleccionesBD";
    public static final int BD_VERSION = 1;

    //TABLA PARTIDOS
    public static final String TABLE_PARTIDOS = "partidos";
    public static final String PARTIDOS_NOMBRE = "nombre";
    public static final String PARTIDOS_ID_PARTIDO = "id_partido";

    //TABLA CANDIDATOS
    public static final String TABLE_CANDIDATOS = "candidatos";
    public static final String CANDIDATOS_NOMBRE = "nombre";
    public static final String CANDIDATOS_ID_CANDIDATO = "id_candidato";

    public static final String CANDIDATOS_ID_PARTIDO = "id_partido";
    public static final String CANDIDATOS_NUM_VOTOS = "num_votos";


    public EleccionesBD(@Nullable Context context) {
        super(context, BD_NAME, null, BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("Usuariosbd.onCreate", BD_NAME + " creating: "
                + TABLE_CANDIDATOS + " y " + TABLE_PARTIDOS);
        try {
            db.beginTransaction();
            //TABLA PARTIDOS
            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + TABLE_PARTIDOS
                    + "( " + PARTIDOS_ID_PARTIDO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + PARTIDOS_NOMBRE + " TEXT NOT NULL)");

            //TABLA CANDIDATOS
            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + TABLE_CANDIDATOS
                    + "( " + CANDIDATOS_ID_CANDIDATO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CANDIDATOS_NOMBRE + " TEXT NOT NULL, "
                    + CANDIDATOS_ID_PARTIDO + " INTEGER NOT NULL, "
                    + CANDIDATOS_NUM_VOTOS + " INTEGER NOT NULL, "
                    + "FOREIGN KEY (" + CANDIDATOS_ID_PARTIDO + ") REFERENCES "
                    + TABLE_PARTIDOS + "(" + PARTIDOS_ID_PARTIDO + "))");

            //LLAMO AL MÉTODO ADDPARTIDOS PARA AÑADIR PARTIDOS


            //LLAMO AL METODO ADDCANDIDATOS PARA AÑADIR CANDIDATOS


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

    private void addPartidos(SQLiteDatabase db, String nombre) {

        Cursor cursor = null;
        ContentValues values = new ContentValues();
        values.put(PARTIDOS_NOMBRE, nombre);
        try {
            db.beginTransaction();
            cursor = db.query(TABLE_PARTIDOS,
                    new String[]{PARTIDOS_ID_PARTIDO},
                    PARTIDOS_NOMBRE + "= ?",
                    new String[]{nombre}, null, null, null, "1");
            if (cursor.getCount() == 0) {
                db.insert(TABLE_PARTIDOS, null, values);
            } else {
                Log.e("addPartidos", "partido ya existe no se puede crear otro igual");
            }
            db.setTransactionSuccessful();
        } catch (SQLException exc) {
            Log.e("DBManager.addPartido", exc.getMessage());
        } finally {
            cursor.close();
            db.endTransaction();
        }

    }

    private void addCandidato(SQLiteDatabase db, String nombre, int idPartido) {

        Cursor cursor = null;
        ContentValues values = new ContentValues();
        values.put(CANDIDATOS_NOMBRE, nombre);
        values.put(CANDIDATOS_ID_PARTIDO, idPartido);
        try {
            db.beginTransaction();
            cursor = db.query(TABLE_PARTIDOS,
                    new String[]{CANDIDATOS_ID_CANDIDATO},
                    CANDIDATOS_NOMBRE + "= ?",
                    new String[]{nombre}, null, null, null, "1");
            if (cursor.getCount() == 0) {
                db.insert(TABLE_CANDIDATOS, null, values);
            } else {
                Log.e("addCandidato", "candidato ya existe no se puede crear otro igual");
            }
            db.setTransactionSuccessful();
        } catch (SQLException exc) {
            Log.e("DBManager.addCandidato", exc.getMessage());
        } finally {
            cursor.close();
            db.endTransaction();
        }

    }



}
