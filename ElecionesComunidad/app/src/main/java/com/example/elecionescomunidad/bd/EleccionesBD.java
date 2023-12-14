package com.example.elecionescomunidad.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.elecionescomunidad.modelos.Candidato;
import com.example.elecionescomunidad.modelos.Partido;

import java.util.ArrayList;

public class EleccionesBD extends SQLiteOpenHelper {
    public static final String BD_NAME = "EleccionesBD";
    public static final int BD_VERSION = 2;

    //TABLA PARTIDOS
    public static final String TABLE_PARTIDOS = "partidos";
    public static final String PARTIDOS_NOMBRE = "nombre";
    public static final String PARTIDOS_ID_PARTIDO = "id_partido";
    public static final String PARTIDOS_COLOR = "color";

    //TABLA CANDIDATOS
    public static final String TABLE_CANDIDATOS = "candidatos";
    public static final String CANDIDATOS_NOMBRE = "nombre";
    public static final String CANDIDATOS_ID_CANDIDATO = "id_candidato";

    public static final String CANDIDATOS_ID_PARTIDO = "id_partido";
    public static final String CANDIDATOS_NUM_VOTOS = "num_votos";
    private static EleccionesBD instancia;

    private EleccionesBD(@Nullable Context context) {
        super(context, BD_NAME, null, BD_VERSION);
    }

    public static EleccionesBD getInstance(Context contexto) {
        if (instancia == null) {
            instancia = new EleccionesBD(contexto);
        }
        return instancia;
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
                    + PARTIDOS_NOMBRE + " TEXT NOT NULL,"
                    + PARTIDOS_COLOR + " INTEGER NOT NULL)");

            //TABLA CANDIDATOS
            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + TABLE_CANDIDATOS
                    + "( " + CANDIDATOS_ID_CANDIDATO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CANDIDATOS_NOMBRE + " TEXT NOT NULL, "
                    + CANDIDATOS_ID_PARTIDO + " INTEGER NOT NULL, "
                    + CANDIDATOS_NUM_VOTOS + " INTEGER DEFAULT 0, "
                    + "FOREIGN KEY (" + CANDIDATOS_ID_PARTIDO + ") REFERENCES "
                    + TABLE_PARTIDOS + "(" + PARTIDOS_ID_PARTIDO + "))");

            //LLAMO AL MÉTODO ADDPARTIDOS PARA AÑADIR PARTIDOS
            cargarPartidos(db);

            //LLAMO AL METODO ADDCANDIDATOS PARA AÑADIR CANDIDATOS
            cargarCandidatos(db);

            db.setTransactionSuccessful();
        } catch (SQLException ex) {
            Log.e("Clientesbd.onCreate", ex.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    private void cargarCandidatos(SQLiteDatabase db) {
        addCandidato(db, "Concha", 1);
        addCandidato(db, "Marisa", 1);
        addCandidato(db, "Vicenta", 1);
        addCandidato(db, "Lucia", 2);
        addCandidato(db, "Belen", 2);
        addCandidato(db, "Bea", 2);
        addCandidato(db, "Juan \"Chorizo\" Cuesta", 3);
        addCandidato(db, "Isabel \"Yerbas\"", 3);
        addCandidato(db, "Mauri", 3);
        addCandidato(db, "Paco", 4);
        addCandidato(db, "Josemi Cuesta", 4);
        addCandidato(db, "Roberto", 4);
        addCandidato(db, "Emilio Delgado", 5);
        addCandidato(db, "Mariano Delgado", 5);
        addCandidato(db, "Jose María", 5);
        Log.i("cargarCandidatos", "termina de cargar los candidatos");

    }

    private void cargarPartidos(SQLiteDatabase db) {
        addPartidos(db, "Radiopactos", Color.MAGENTA);
        addPartidos(db, "La pija y amigas", Color.rgb(255, 128, 0));
        addPartidos(db, "Esta, nuestra Comunidad", Color.BLUE);
        addPartidos(db, "Los pibes del videoclub", Color.CYAN);
        addPartidos(db, "Un poquito de porfavoh", Color.RED);
        Log.i("cargarPartidos", "termina de cargar los partidos");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void addPartidos(SQLiteDatabase db, String nombre, int color) {

        Cursor cursor = null;
        ContentValues values = new ContentValues();
        values.put(PARTIDOS_NOMBRE, nombre);
        values.put(PARTIDOS_COLOR, color);
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
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
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
            cursor = db.query(TABLE_CANDIDATOS,
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
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            db.endTransaction();
        }

    }

    public ArrayList<Candidato> obtenerCandidatos() {
        Log.i("getCandidatos.inicio", "Inicia el método obtenerCandidatos");

        ArrayList<Candidato> listaCandidatos = new ArrayList<>();
        SQLiteDatabase bd = getReadableDatabase();
        Cursor cursor = null;

        try {
            bd.beginTransaction();
            cursor = bd.query(TABLE_CANDIDATOS, null, null,
                    null, null, null, null, null);

            while (cursor.moveToNext()) {
                int idCandidatoIndex = cursor.getColumnIndex(CANDIDATOS_ID_CANDIDATO);
                int nombreCandidatoIndex = cursor.getColumnIndex(CANDIDATOS_NOMBRE);
                int idPartidoIndex = cursor.getColumnIndex(CANDIDATOS_ID_PARTIDO);
                int numVotosIndex = cursor.getColumnIndex(CANDIDATOS_NUM_VOTOS);

                int idCandidato = cursor.getInt(idCandidatoIndex);
                String nombreCandidato = cursor.getString(nombreCandidatoIndex);
                int idPartido = cursor.getInt(idPartidoIndex);
                int numVotos = cursor.getInt(numVotosIndex);

                // Obtener el partido asociado al candidato
                Partido partido = obtenerPartidoPorId(idPartido);

                // Crear objeto Candidato y agregarlo a la lista
                Candidato candidato = new Candidato(idCandidato, nombreCandidato, partido, numVotos);
                listaCandidatos.add(candidato);
            }

            bd.setTransactionSuccessful();
        } catch (SQLException exc) {
            Log.e("obtenerCandidatos.Error", exc.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            bd.endTransaction();
        }

        return listaCandidatos;
    }

    public Partido obtenerPartidoPorId(int idPartido) {
        Log.i("obtenerPartido.inicio", "Inicia el método obtenerPartidoPorId");
        SQLiteDatabase db = getReadableDatabase();
        Partido partido = null;
        Cursor cursor = null;

        try {
            db.beginTransaction();

            String[] selectionArgs = {String.valueOf(idPartido)};
            cursor = db.query(TABLE_PARTIDOS, null, PARTIDOS_ID_PARTIDO + "=?",
                    selectionArgs, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int nombrePartidoIndex = cursor.getColumnIndex(PARTIDOS_NOMBRE);
                int colorPartidoIndex = cursor.getColumnIndex(PARTIDOS_COLOR);

                String nombrePartido = cursor.getString(nombrePartidoIndex);
                int colorPartido = cursor.getInt(colorPartidoIndex);

                partido = new Partido(nombrePartido, colorPartido, idPartido);
            }
            db.setTransactionSuccessful();

        } catch (SQLException exc) {
            Log.e("obtenerPartido.Error", exc.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            db.endTransaction();

        }

        return partido;
    }

//VERSION 1

    public boolean addVotosCandidatos(ArrayList<Candidato> candidatos) {
        SQLiteDatabase db = getWritableDatabase();
        Log.i("addVoto.inicio", "Inicia el método addVoto");
        int filasActualizadas = 0;

        try {
            db.beginTransaction();
            for (Candidato c : candidatos) {


                // 1. Obtén el valor actual de CANDIDATOS_NUM_VOTOS
                int votosActuales = 0;
                Cursor cursor = db.rawQuery("SELECT " + CANDIDATOS_NUM_VOTOS + " FROM " + TABLE_CANDIDATOS +
                        " WHERE " + CANDIDATOS_ID_CANDIDATO + " = ?", new String[]{String.valueOf(c.getId())});

                if (cursor.moveToFirst()) {
                    votosActuales = cursor.getInt(cursor.getColumnIndexOrThrow(CANDIDATOS_NUM_VOTOS));
                }

                cursor.close();

                // 2. Incrementa el valor
                int nuevosVotos = votosActuales + 1;

                // 3. Actualiza la base de datos con el nuevo valor
                ContentValues valores = new ContentValues();
                valores.put(CANDIDATOS_NUM_VOTOS, nuevosVotos);

                filasActualizadas = db.update(TABLE_CANDIDATOS, valores,
                        CANDIDATOS_ID_CANDIDATO + " =?",
                        new String[]{String.valueOf(c.getId())});
            }
            db.setTransactionSuccessful();
        } catch (SQLException exc) {
            Log.e("addVoto.Error", exc.getMessage());
        } finally {
            db.endTransaction();
        }

        return filasActualizadas > 0;
    }

    //    vVERSION 2(menos codigo)
    public boolean addVotosCandidatos2(ArrayList<Candidato> candidatos) {
        SQLiteDatabase db = getWritableDatabase();
        Log.i("addVoto.inicio", "Inicia el método addVoto");
        int filasActualizadas = 0;

        try {
            db.beginTransaction();
            for (Candidato c : candidatos) {

                // 1. Obtén el valor actual de CANDIDATOS_NUM_VOTOS
                int votosActuales = 0;
                Cursor cursor = db.rawQuery("SELECT " + CANDIDATOS_NUM_VOTOS + " FROM " + TABLE_CANDIDATOS +
                        " WHERE " + CANDIDATOS_ID_CANDIDATO + " = ?", new String[]{String.valueOf(c.getId())});

                if (cursor.moveToFirst()) {
                    votosActuales = cursor.getInt(cursor.getColumnIndexOrThrow(CANDIDATOS_NUM_VOTOS));
                }

                cursor.close();

                // 2. Incrementa el valor
                int nuevosVotos = votosActuales + 1;

                // 3. Actualiza la base de datos con el nuevo valor
                ContentValues valores = new ContentValues();
                valores.put(CANDIDATOS_NUM_VOTOS, nuevosVotos);

                filasActualizadas = db.update(TABLE_CANDIDATOS, valores,
                        CANDIDATOS_ID_CANDIDATO + " =?",
                        new String[]{String.valueOf(c.getId())});
            }
            db.setTransactionSuccessful();
        } catch (SQLException exc) {
            Log.e("addVoto.Error", exc.getMessage());
        } finally {
            db.endTransaction();
        }

        return filasActualizadas > 0;
    }
}
