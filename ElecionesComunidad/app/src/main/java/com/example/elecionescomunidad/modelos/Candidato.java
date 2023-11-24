package com.example.elecionescomunidad.modelos;

import android.os.Build;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Candidato {
    private int id;
    private String nombre;
    private Partido partido;
    private int numVotos;

    public Candidato(int id, String nombre, Partido partido, int numVotos) {
        this.id = id;
        this.nombre = nombre;
        this.partido = partido;
        this.numVotos = numVotos;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Partido getPartido() {
        return partido;
    }

    public int getNumVotos() {
        return numVotos;
    }
    public static void sort(ArrayList<Candidato> candidatos) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // Utilizar expresión lambda para ordenar en versiones Nougat o superiores
            candidatos.sort(Comparator.comparingInt(Candidato::getNumVotos));
        } else {
            // Utilizar un Comparator externo para versiones anteriores a Nougat
            Collections.sort(candidatos, (o1, o2) -> Integer.compare(o1.getNumVotos(), o2.getNumVotos()));
        }
    }
    @Override
    public String toString() {
        return "Candidato{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", partido=" + partido +
                ", numVotos=" + numVotos +
                '}';
    }
}
