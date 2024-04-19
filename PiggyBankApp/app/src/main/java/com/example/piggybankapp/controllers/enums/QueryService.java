package com.example.piggybankapp.controllers.enums;

public enum QueryService {
    ID_CUENTA{ @Override public String value() { return ""; } };
    public abstract String value();
}
