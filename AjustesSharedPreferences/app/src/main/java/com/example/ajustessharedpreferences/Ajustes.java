package com.example.ajustessharedpreferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
public class Ajustes {
    private String nombre, correo, edad;
    private boolean recibirPublicidad;
    private SharedPreferences sp;
    private final String CLAVE_NOMBRE="nombre";
    private final String CLAVE_CORREO="correo";
    private final String CLAVE_EDAD="edad";
    private final String CLAVE_RECIBIR_PUBLICIDAD="recibirPublicidad";
    //region gets & sets
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public int getEdad(int valorSiNoHayEdad) {
        int edad;
        try { edad=Integer.parseInt(this.edad);}
        catch (NumberFormatException e) { return valorSiNoHayEdad; }
        return edad;
    }
    public boolean getRecibirPublicidad() { return recibirPublicidad; }
    public boolean set(String nombre,String correo,String edad, boolean recibirPublicidad ) {
        if(nombre.trim().equals("")) return false;
        if(!correo.equals("")) if(!esCorreoValido(correo)) return false;
        if(!edad.equals("")) if(!esEnteroPositivo(edad)) return false;
        this.nombre=nombre;
        this.correo=correo;
        this.edad=edad;
        this.recibirPublicidad=recibirPublicidad;
        sp.edit()
                .putString(CLAVE_NOMBRE,nombre)
                .putString(CLAVE_CORREO,correo)
                .putString(CLAVE_EDAD,edad)
                .putBoolean(CLAVE_RECIBIR_PUBLICIDAD,recibirPublicidad)
                .apply();
        return true;
    }
    //endregion
//region Implementación SINGLETON
    private static Ajustes instancia=null;
    public static Ajustes getInstance(Activity activity) {
        if(instancia==null)
            instancia=new Ajustes(activity);
        return instancia;
    }
    // Singleton con constructor privado con un parámetro
// con lo cual ya no existirá un constructor por defecto ()
    private Ajustes(Activity activity) {
        sp=activity.getPreferences(Context.MODE_PRIVATE);
        nombre=sp.getString(CLAVE_NOMBRE,"");
        correo=sp.getString(CLAVE_CORREO,"");
        edad=sp.getString(CLAVE_EDAD,"");
        recibirPublicidad=sp.getBoolean(CLAVE_RECIBIR_PUBLICIDAD,false);
    }
    //endregion
    static boolean esCorreoValido(String correo) {
// expresión regular mejorable en los caracteres especiales aceptados
        return correo.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]{2,}");
    }
    static boolean esEnteroPositivo(String numero) {
        return numero.matches("\\d+");
    }
}
