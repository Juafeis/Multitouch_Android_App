package com.example.dimappfinal.Ejercicio_1;

import android.view.View;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyView extends View {
    // Generador de n�meros aleatorios
    Random random = new Random();
    // Almacena las propiedades gr�ficas de dibujo de la l�nea
    Paint paint = new Paint();
    // Almacenan la posici�n inicial y final de la l�nea
    float prevX, prevY, newX, newY;
    // Almacena el color con el que se va a dibujar la l�nea actual
    int color = Color.BLACK;
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Establezco las propiedades gr�ficas de dibujo de la l�nea
        paint.setAntiAlias(true);
        paint.setStrokeWidth(6f);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Establezco el color con el que se va a dibujar la l�nea en curso
        paint.setColor(this.color);
        // Dibujo la l�nea en curso
        canvas.drawLine(this.prevX, this.prevY, this.newX,
                this.newY, this.paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN :
                prevX = event.getX();
                prevY = event.getY();
                this.color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
                break;
            case MotionEvent.ACTION_MOVE :
                newX = event.getX();
                newY = event.getY();
                this.invalidate();
                break;
            case MotionEvent.ACTION_UP :
                prevX = prevY = newX = newY = -1;
                break;
        }

        return true;
    }


}
