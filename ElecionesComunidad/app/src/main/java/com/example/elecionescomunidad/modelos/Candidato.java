package com.example.elecionescomunidad.modelos;

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
