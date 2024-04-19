package com.example.piggybankapp.models;

public class Saldos {

    private int id;
    private int cuenta;
    private int saldoGeneral;
    private int ingresos;
    private int gastos;


    public Saldos() {
    }

    public Saldos(int id, int cuenta, int saldoGeneral, int ingresos, int gastos) {
        this.id = id;
        this.cuenta = cuenta;
        this.saldoGeneral = saldoGeneral;
        this.ingresos = ingresos;
        this.gastos = gastos;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCuenta() {
        return cuenta;
    }

    public void setCuenta(int cuenta) {
        this.cuenta = cuenta;
    }

    public int getSaldoGeneral() {
        return saldoGeneral;
    }

    public void setSaldoGeneral(int saldoGeneral) {
        this.saldoGeneral = saldoGeneral;
    }

    public int getIngresos() {
        return ingresos;
    }

    public void setIngresos(int ingresos) {
        this.ingresos = ingresos;
    }

    public int getGastos() {
        return gastos;
    }

    public void setGastos(int gastos) {
        this.gastos = gastos;
    }
}
