package com.example.piggybankapp.models;

import java.util.Date;

public class Cuenta {

    private int id;
    private String nombre;
    private int cuenta;
    private String ultimaSesion;


    public Cuenta() {
    }

    public Cuenta(int id, String nombre, int cuenta, String ultimaSesion) {
        this.id = id;
        this.nombre = nombre;
        this.cuenta = cuenta;
        this.ultimaSesion = ultimaSesion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCuenta() {
        return cuenta;
    }

    public void setCuenta(int cuenta) {
        this.cuenta = cuenta;
    }

    public String getUltimaSesion() {
        return ultimaSesion;
    }

    public void setUltimaSesion(String ultimaSesion) {
        this.ultimaSesion = ultimaSesion;
    }
}
