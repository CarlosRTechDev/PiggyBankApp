package com.example.piggybankapp.models;

public class Tarjetas {

    private int id;
    private String nombre;
    private int tarjeta;
    private int cuenta;
    private double saldo;
    private String estado;
    private String tipo;


    public Tarjetas() {
    }

    public Tarjetas(int id, String nombre, int tarjeta, int cuenta, double saldo, String estado, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.tarjeta = tarjeta;
        this.cuenta = cuenta;
        this.saldo = saldo;
        this.estado = estado;
        this.tipo = tipo;
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

    public int getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(int tarjeta) {
        this.tarjeta = tarjeta;
    }

    public int getCuenta() {
        return cuenta;
    }

    public void setCuenta(int cuenta) {
        this.cuenta = cuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
