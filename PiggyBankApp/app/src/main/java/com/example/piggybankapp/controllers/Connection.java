package com.example.piggybankapp.controllers;

import android.content.Context;

import com.example.piggybankapp.controllers.enums.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Connection {

    private API urlBase;
    private API apiService;
    private String queryService;

    private Map<String, String> headers = new HashMap<>();
    private JSONObject jsonObject = new JSONObject();
    private JSONObject jsonObjectBackup = new JSONObject();
    private JSONArray jsonArray = new JSONArray();
    private String textBody = "";
    private String query = "";
    private Context context;

    public Connection(Context context, API urlBase, API apiService, String queryService){
        this.context = context;
        this.urlBase = urlBase;
        this.apiService = apiService;
        this.queryService = queryService;
    }

    //CHA: para Servicio POST
    public Connection(Context context, API urlBase, API apiService){
        this.context = context;
        this.urlBase = urlBase;
        this.apiService = apiService;
    }

    public String getUrl(){
        return this.urlBase.value() + this.apiService.value() + this.queryService;
    }

    public String getUrlWithoutQuery(){
        return this.urlBase.value() + this.apiService.value();
    }

    public Map<String, String> getHeaders(){
        return headers;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public void addIntegerIntoJSON(String key, int value) {
        try {
            jsonObject.put(key, value);
            jsonObjectBackup.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addStringIntoJSON(String key, String value) {
        try {
            jsonObject.put(key, value);
            jsonObjectBackup.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addDoubleIntoJSON(String key, double value) {
        try {
            jsonObject.put(key, value);
            jsonObjectBackup.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String jsonObjectToString() {
        if (jsonObject != null) {
            return jsonObject.toString();
        }
        return "";
    }

    public String arrayToString() {
        if (jsonArray != null) {
            return jsonArray.toString();
        }
        return null;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    // Uso para metodos de tipo GET
    public void addQuery(String key, String value) {

        if (key != null) {
            query += key + "=" + value;
        } else {
            query += value;
        }
        //System.out.println("test3: " +query);
    }

    public String getQuery() {
        return this.query;
    }
}
