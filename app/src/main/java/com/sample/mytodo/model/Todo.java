package com.sample.mytodo.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Todo {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String dateString;
    @ColumnInfo
    private String text;

    @ColumnInfo
    private boolean favorite;
    @ColumnInfo
    private boolean done;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public Date getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
        try {
            return sdf.parse(dateString);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void setDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
        dateString = sdf.format(date);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
