package com.david.smart_home.util;

import java.time.LocalDateTime;

public class Util {

    public static String generarStatus(){
        // la bateria va del 0 al 100
        int battery = (int) Math.floor(Math.random()*101);
        // el consumo de watios va de 200 a 1000
        int watts = (int) Math.floor(Math.random()*801) + 200;
        return "battery: " + battery + "; consumption: " + watts;
    }

    public static String formatearFechas(LocalDateTime fecha) {
        return fecha.getYear() + "-" + fecha.getMonthValue() + "-" +fecha.getDayOfMonth() + "T" + fecha.getHour() + ":" + fecha.getMinute() + ":" + fecha.getSecond();
    }
}
