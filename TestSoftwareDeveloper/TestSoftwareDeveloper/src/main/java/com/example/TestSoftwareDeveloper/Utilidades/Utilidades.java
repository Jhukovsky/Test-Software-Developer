package com.example.TestSoftwareDeveloper.Utilidades;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@Component
public class Utilidades {

    public Date conversionStringDate(String horaEntrada){
        String formato = "yyyy-MM-dd HH:mm:ss"; // Formato de la fecha y hora

        SimpleDateFormat format = new SimpleDateFormat(formato);
        Date date;
        try {
             date = format.parse(horaEntrada);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }
}
