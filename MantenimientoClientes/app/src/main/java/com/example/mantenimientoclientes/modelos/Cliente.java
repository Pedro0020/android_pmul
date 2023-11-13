package com.example.mantenimientoclientes.modelos;

public class Cliente {
    private String nombre;
    private String apellidos;
    private String dni;
    private String provincia;
    private int vip;
    private double latitud;
    private double longitud;

    public Cliente(String nombre, String apellidos, String dni, String provincia,
                   int vip, double latitud, double longitud) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.provincia = provincia;
        this.vip = vip;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDni() {
        return dni;
    }

    public int getVip() {
        return vip;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public String getProvincia() {
        return provincia;
    }

    @Override
    public String toString() {
        return apellidos + ", " + nombre;
    }
}
