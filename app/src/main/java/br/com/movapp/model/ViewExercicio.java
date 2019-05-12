package br.com.movapp.model;

/**
 * Created by anupamchugh on 11/02/17.
 */

public class ViewExercicio {
    public Long id;
    public String text;
    public byte[] drawable;
    public String color;

    public ViewExercicio(Long id, String text, byte[] drawable, String color) {
        this.id = id;
        this.text = text;
        this.drawable = drawable;
        this.color = color;
    }
}
