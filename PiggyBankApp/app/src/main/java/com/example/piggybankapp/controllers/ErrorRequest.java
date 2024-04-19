package com.example.piggybankapp.controllers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;


public class ErrorRequest {
    private static boolean isOK = false;

    public static String onErrorResponse(Context context, VolleyError error) {
        isOK = false;
        if (error instanceof NoConnectionError) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if (cm != null) {
                networkInfo = cm.getActiveNetworkInfo();
            }
            if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                return "No se puede resolver el host, revisa la URL.";
            } else {
                return "No tienes una conexión a Internet, activa los datos o el Wi-Fi.";
            }
        } else if (error instanceof AuthFailureError) {
            return "Error 401: Autorización requerida";
        } else if (error instanceof TimeoutError) {
            return "Error 504: Tiempo de espera agotado, verifica tu conexión a Internet.";
        } else if (error instanceof ServerError) {
            return "Error del servidor, inténtalo de nuevo más tarde.";
        } else if (error instanceof NetworkError) {
            return "Error de red, verifica tu conexión a Internet.";
        } else if (error instanceof ParseError) {
            return "Error de análisis, verifica el formato de los datos.";
        }

        return "Error desconocido.";
    }
}
