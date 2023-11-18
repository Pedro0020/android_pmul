package com.example.elecionescomunidad.modelos;

public class Partido {
    private String nombre;
    private int color;
    private int idPartido;

    public Partido(String nombre, int color, int idPartido) {
        this.nombre = nombre;
        this.color = color;
        this.idPartido = idPartido;
    }

    public String getNombre() {
        return nombre;
    }

    public int getColor() {
        return color;
    }

    public int getIdPartido() {
        return idPartido;
    }

    @Override
    public String toString() {
        return "Partido{" +
                "nombre='" + nombre + '\'' +
                ", color=" + color +
                ", idPartido=" + idPartido +
                '}';
    }
}

