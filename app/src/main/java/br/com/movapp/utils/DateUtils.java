package br.com.movapp.utils;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtils {

    public static String getToday(){
        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        return  date;
    }

    public static String getDayOfWeek(){
        Locale myLocale = new Locale("pt", "BR");
        String weekdays[] = new DateFormatSymbols(myLocale).getWeekdays();

        Calendar c = Calendar.getInstance();
        Date date = new Date();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        String diaSemana = weekdays[dayOfWeek];
        diaSemana = diaSemana.substring(0, 1).toUpperCase() + diaSemana.substring(1);

        return diaSemana;
    }

    public static Date convertData(String data){
        if (data == null || data.equals(""))
            return null;
        Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = (java.util.Date)formatter.parse(data);
        } catch (ParseException e) {
            return null;
        }
        return date;
    }

}
