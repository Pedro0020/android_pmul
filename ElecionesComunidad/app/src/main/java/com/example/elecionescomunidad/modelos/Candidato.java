package com.example.elecionescomunidad.modelos;

public class Candidato {
    private int id;
    private String nombre;
    private String nombrePartido;
    private int colorPartido;
    private int numVotos;

    public Candidato(int id, String nombre, String nombrePartido, int colorPartido, int numVotos) {
        this.id = id;
        this.nombre = nombre;
        this.nombrePartido = nombrePartido;
        this.colorPartido = colorPartido;
        this.numVotos = numVotos;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombrePartido() {
        return nombrePartido;
    }

    public int getColorPartido() {
        return colorPartido;
    }

    public int getNumVotos() {
        return numVotos;
    }
}
