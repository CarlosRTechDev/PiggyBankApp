package com.example.piggybankapp.controllers;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Util {

    public static String convertirStringACalendar(String fechaString, String formato) throws ParseException {
        Date date = convertirStringASimpleDF(fechaString, formato);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int año = calendar.get(Calendar.YEAR);
        String mes = convertirMes(calendar.get(Calendar.MONTH));
        int día = calendar.get(Calendar.DAY_OF_MONTH);
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minuto = calendar.get(Calendar.MINUTE);
        int segundo = calendar.get(Calendar.SECOND);

        return "" + día + "/" + mes + "/" + año;
    }

    public static Date convertirStringASimpleDF(String fechaString, String formato) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        return sdf.parse(fechaString);
    }

    public static String convertirSDFA12Horas(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        return sdf.format(date);
    }

    public static String convertirMes(int intMes) {
        // Los meses en Calendar van de 0 a 11
        String mes = "";
        switch (intMes) {
            case 0:
                mes = "Enero";
                break;
            case 1:
                mes = "Febrero";
                break;
            case 2:
                mes = "Marzo";
                break;
            case 3:
                mes = "Abril";
                break;
            case 4:
                mes = "Mayo";
                break;
            case 5:
                mes = "Junio";
                break;
            case 6:
                mes = "Julio";
                break;
            case 7:
                mes = "Agosto";
                break;
            case 8:
                mes = "Septiembre";
                break;
            case 9:
                mes = "Octubre";
                break;
            case 10:
                mes = "Noviembre";
                break;
            case 11:
                mes = "Diciembre";
                break;
        }
        return mes;
    }

    public static String formatNumber(double number) {
        if (number == 0) {
            return DecimalFormat.getInstance().format(number);
        } else {
            DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
            return decimalFormat.format(number);
        }
    }

}
