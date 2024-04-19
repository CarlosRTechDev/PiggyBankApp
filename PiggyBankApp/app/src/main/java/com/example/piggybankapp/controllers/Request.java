package com.example.piggybankapp.controllers;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;


public class Request {
    private RequestQueue requestQueue;
    //private RequestQueue mRequestQueue;
    private Connection connection;
    private Context context;
    private static final int TIMEOUT = 60;
    private static final String PROTOCOL_CHARSET = "utf-8";
    //private ProgressDialogCustom progressDialogCustom;

    public enum CONTENT_TYPE {
        JSON,
        ARRAY,
        TEXT,
        NONE
    }

    /**
     * REQUIRED
     */
    public Request(Context context, Connection connection) {
        //mRequestQueue = Volley.newRequestQueue(context);
        this.connection = connection;
        this.context = context;
        this.requestQueue = Singleton.getInstance(context).getmRequestQueue();
        //this.requestQueue = mRequestQueue;
        //progressDialogCustom = new ProgressDialogCustom(context);
    }

    private void printRequestGET() {
    }

    private void printResponse(String response) {

    }

    private void printResponseError(VolleyError error) {

    }


    public void get(RequestCallback.Resolve resolve, RequestCallback.Eject eject) {
        //progressDialogCustom.show();
        //printRequestGET();
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, this.connection.getUrl() + connection.getQuery(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                printResponse(response);
                resolve.onResponse(response);
                //dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printResponseError(error);
                eject.onErrorRequest(error);
                //dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (connection.getHeaders().isEmpty()) {
                    return super.getHeaders();
                } else {
                    return connection.getHeaders();
                }
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT * 1000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(stringRequest);
    }



    public void post(RequestCallback.Resolve resolve, RequestCallback.Eject eject, CONTENT_TYPE contentType) {
        /*if (context instanceof Activity) {
        //progressDialogCustom.show();
        }*/
        String requestBody = "";
        String headerContent = "";
        switch (contentType) {
            case JSON:
                requestBody = connection.jsonObjectToString();
                headerContent = "application/json; charset=utf-8";
                break;
            case TEXT:
                requestBody = connection.getTextBody();
                headerContent = "text/html; charset=utf-8";
                break;
            case ARRAY:
                requestBody = connection.arrayToString();
                headerContent = "application/json; charset=utf-8";
                break;
            case NONE:
                requestBody = null;
                headerContent = "application/json; charset=utf-8";
                break;
        }
        String finalRequestBody = requestBody;
        String finalHeaderContent = headerContent;
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, this.connection.getUrlWithoutQuery(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                printResponse(response);
                resolve.onResponse(response);
                //dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    printResponseError(error);
                    eject.onErrorRequest(error);
                    //dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (connection.getHeaders().isEmpty()) {
                    return super.getHeaders();
                } else {
                    return connection.getHeaders();
                }
            }

            @Override
            public String getBodyContentType() {
                return finalHeaderContent;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return finalRequestBody == null ? null : finalRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", finalRequestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
//
                    ///Solo si es 201 es correcto
                    if (response.statusCode == 201) {

                        String jsonString = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

                        JSONObject jsonResponse = new JSONObject();
                        jsonResponse.put("data", jsonString);
                        jsonResponse.put("headers", new JSONObject(response.headers));

                        return super.parseNetworkResponse(response);
                    }
                    ///Si es otro codigo 200 entonces lo manejamos como error
                    if (response.statusCode < 300) {
                        String string = new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                        return Response.error(new ParseError(new Exception(string)));
                    }

                } catch (Exception e) {
                }
                return super.parseNetworkResponse(response);
            }


        };

        // Add the request to the RequestQueue.
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT * 1000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        requestQueue.add(stringRequest);
    }


    public interface RequestCallback {
        interface Resolve {
            void onResponse(String response);
        }

        interface Eject {
            void onErrorRequest(VolleyError error);
        }
    }
}
