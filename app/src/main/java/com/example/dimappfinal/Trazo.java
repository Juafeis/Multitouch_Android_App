package com.example.dimappfinal;

import android.graphics.Path;

import androidx.annotation.Nullable;

public class Trazo extends Path {
    int grosor;
    int color;
    float posActualX;
    float posActualY;
    float dimension;

    public Trazo() {
    }

    public Trazo(@Nullable Path src) {
        super(src);
    }

    public int getGrosor() {
        return grosor;
    }

    public int getColor() {
        return color;
    }

    public void setGrosor(int grosor) {
        this.grosor = grosor;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getPosActualX() {
        return posActualX;
    }

    public float getPosActualY() {
        return posActualY;
    }

    public void setPosActualX(float posActualX) {
        this.posActualX = posActualX;
    }

    public void setPosActualY(float posActualY) {
        this.posActualY = posActualY;
    }

    public float getDimension() {
        return dimension;
    }
    public void setDimension(float dimension) {
        this.dimension = dimension;
    }

}
