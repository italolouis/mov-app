package br.com.movapp.model;

/**
 * Created by anupamchugh on 11/02/17.
 */

public class DataModel {


    public String text;
    public byte[] drawable;
    public String color;

    public DataModel(String t, byte[]  d, String c )
    {
        text=t;
        drawable=d;
        color=c;
    }
}
