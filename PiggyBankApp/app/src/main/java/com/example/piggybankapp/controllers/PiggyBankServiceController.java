package com.example.piggybankapp.controllers;

import android.content.Context;
import android.content.res.Resources;

import com.example.piggybankapp.controllers.enums.API;
import com.example.piggybankapp.controllers.enums.QueryService;
import com.example.piggybankapp.models.Cuenta;
import com.example.piggybankapp.models.Movimientos;
import com.example.piggybankapp.models.Saldos;
import com.example.piggybankapp.models.Tarjetas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PiggyBankServiceController {

    private Context appContext;
    private Connection connection;
    private Resources resources;
    private CallbackService callback;

    /** Constructor con el contexto de la app (NECESARIO)*/
    public PiggyBankServiceController(Context appContext, CallbackService callback){
        this.appContext = appContext;
        this.resources = appContext.getResources();
        this.callback = callback;
    }

    //GET Cuenta
    public void getCuenta(CallbackCuenta callbackCuenta) {
        connection = new Connection(appContext, API.BASE_URL, API.CUENTA,
                QueryService.ID_CUENTA.value());

        connection.addQuery("id", "201224065");
        new Request(appContext, connection)
                .get(response -> {
                    Cuenta cuenta = null;

                    try {
                        JSONObject jo = new JSONObject(response);
                        cuenta = new Cuenta();

                        cuenta.setNombre(jo.getString("nombre"));
                        cuenta.setCuenta(jo.getInt("cuenta"));
                        cuenta.setUltimaSesion(jo.getString("ultimaSesion"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    callbackCuenta.onFinish(cuenta);
                }, error -> callbackCuenta.onError(ErrorRequest.onErrorResponse(appContext, error)));
    }


    //GET Saldo
    public void getSaldos(CallbackSaldos callbackSaldos) {
        connection = new Connection(appContext, API.BASE_URL, API.SALDOS,
                QueryService.ID_CUENTA.value());

        connection.addQuery("id", "201224065");
        new Request(appContext, connection)
                .get(response -> {
                    Saldos saldos = null;
                    try {
                        JSONObject jo = new JSONObject(response);
                        saldos = new Saldos();
                        saldos.setSaldoGeneral(jo.getInt("saldoGeneral"));
                        saldos.setIngresos(jo.getInt("ingresos"));
                        saldos.setGastos(jo.getInt("gastos"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    callbackSaldos.onFinish(saldos);
                }, error -> callbackSaldos.onError(ErrorRequest.onErrorResponse(appContext, error)));
    }

    //GET Tarjetas
    public void getTarjetas(CallbackTarjetas callbackTarjetas) {
        connection = new Connection(appContext, API.BASE_URL, API.TARJETAS,
                QueryService.ID_CUENTA.value());

        connection.addQuery("id", "201224065");
        new Request(appContext, connection)
                .get(response -> {

                    Tarjetas[] tarjetas = new Tarjetas[0];

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        //JSONObject jo = new JSONObject(response);
                        tarjetas = new Tarjetas[jsonArray.length()];

                        //tarjetas = new Tarjetas[0];

                        for(int i = 0; i < jsonArray.length(); i++){
                            tarjetas[i] = new Tarjetas();
                            tarjetas[i].setId(jsonArray.getJSONObject(i).getInt("id"));
                            tarjetas[i].setNombre(jsonArray.getJSONObject(i).getString("nombre"));
                            tarjetas[i].setTarjeta(jsonArray.getJSONObject(i).getInt("tarjeta"));
                            tarjetas[i].setCuenta(jsonArray.getJSONObject(i).getInt("cuenta"));
                            tarjetas[i].setSaldo(jsonArray.getJSONObject(i).getDouble("saldo"));
                            tarjetas[i].setEstado(jsonArray.getJSONObject(i).getString("estado"));
                            tarjetas[i].setTipo(jsonArray.getJSONObject(i).getString("tipo"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    callbackTarjetas.onFinish(tarjetas);
                }, error -> callbackTarjetas.onError(ErrorRequest.onErrorResponse(appContext, error)));
    }

    //GET Movimientos
    public void getMovimientos(CallbackMovimientos callbackMovimientos) {
        connection = new Connection(appContext, API.BASE_URL, API.MOVIMIENTOS,
                QueryService.ID_CUENTA.value());

        connection.addQuery("id", "201224065");
        new Request(appContext, connection)
                .get(response -> {
                    Movimientos[] movimientos = new Movimientos[0];
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        movimientos = new Movimientos[jsonArray.length()];

                        for(int i = 0; i < jsonArray.length(); i++){
                            movimientos[i] = new Movimientos();
                            movimientos[i].setDescripcion(jsonArray.getJSONObject(i).getString("descripcion"));
                            movimientos[i].setFecha(jsonArray.getJSONObject(i).getString("fecha"));
                            movimientos[i].setMonto(jsonArray.getJSONObject(i).getDouble("monto"));
                            movimientos[i].setTipo(jsonArray.getJSONObject(i).getString("tipo"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    callbackMovimientos.onFinish(movimientos);
                }, error -> callbackMovimientos.onError(ErrorRequest.onErrorResponse(appContext, error)));
    }


    //POST Agregar Tarjeta
    public void crearTarjeta(String nombreTitular, String tipoTarjeta) {
        connection = new Connection(appContext, API.BASE_URL, API.AGREGAR_TARJETA);
        connection.addStringIntoJSON("nombre", nombreTitular);
        connection.addStringIntoJSON("tipo", tipoTarjeta);

        new Request(appContext, connection)
                .post(response -> callback.onFinish(response),
                        error -> callback.onError(ErrorRequest.onErrorResponse(appContext, error)),
                        Request.CONTENT_TYPE.JSON);
    }

    //POST Bloquear/Desbloquear Tarjeta
    public void bloquearDesbloquearTarjeta(int idTarjeta, String nombre, int numTarjeta, int numCuenta,
                                           Double saldo, String estatus, String tipoTarjeta) {

        connection = new Connection(appContext, API.BASE_URL, API.ACTUALIZAR_TARJETA);
        connection.addIntegerIntoJSON("id", idTarjeta);
        connection.addStringIntoJSON("nombre", nombre);
        connection.addIntegerIntoJSON("tarjeta", numTarjeta);
        connection.addIntegerIntoJSON("cuenta", numCuenta);
        connection.addDoubleIntoJSON("saldo", saldo);
        connection.addStringIntoJSON("estado", estatus);
        connection.addStringIntoJSON("tipo", tipoTarjeta);

        new Request(appContext, connection)
                .post(response -> callback.onFinish(response),
                        error -> callback.onError(ErrorRequest.onErrorResponse(appContext, error)),
                        Request.CONTENT_TYPE.JSON);
    }


    public interface CallbackCuenta {
        void onFinish(Cuenta cuenta);
        void onError(String message);
    }

    public interface CallbackSaldos {
        void onFinish(Saldos saldos);
        void onError(String message);
    }

    public interface CallbackTarjetas {
        void onFinish(Tarjetas[] tarjetas);
        void onError(String message);
    }

    public interface CallbackMovimientos {
        void onFinish(Movimientos[] movimientos);
        void onError(String message);
    }
}
