package com.example.dimappfinal;

public class Linea {
    float X;
    float Y;
    float newX;
    float newY;
    int pintura;

    public Linea(float x, float y, float newX, float newY, int pintura) {
        X = x;
        Y = y;
        this.newX = newX;
        this.newY = newY;
        this.pintura = pintura;
    }

    public float getX() {
        return X;
    }

    public float getY() {
        return Y;
    }

    public float getNewX() {
        return newX;
    }

    public float getNewY() {
        return newY;
    }

    public int getPintura() {
        return pintura;
    }


}
