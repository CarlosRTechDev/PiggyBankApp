package com.example.piggybankapp.controllers.enums;


public enum API {
    BASE_URL{ @Override public String value() { return "http://AddYourURLHere/piggybank-db/"; } },
    CUENTA{ @Override public String value() { return "cuenta/"; } },
    SALDOS{ @Override public String value() { return "saldos/"; } },
    TARJETAS{ @Override public String value() { return "tarjetas/"; } },
    AGREGAR_TARJETA{ @Override public String value() { return "guardarTarjeta"; } },
    ACTUALIZAR_TARJETA{ @Override public String value() { return "actualizarTarjeta"; } },
    MOVIMIENTOS{ @Override public String value() { return "movimientos/"; } };

    public abstract String value();
}

