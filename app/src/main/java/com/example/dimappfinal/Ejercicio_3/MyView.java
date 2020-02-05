package com.example.dimappfinal.Ejercicio_3;

import android.view.View;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import java.util.HashMap; // import the HashMap class
import android.graphics.Path;

import com.example.dimappfinal.Trazo;

public class MyView extends View {
    // Generador de n�meros aleatorios
    Random random = new Random();
    // Almacena las propiedades gr�ficas de dibujo de la l�nea
    Paint paint = new Paint();
    // Almacenan la posici�n inicial y final de la l�nea
    int color = Color.BLACK;
    private HashMap<Integer, Trazo> trazos = new HashMap<>();
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
        for(int i = 0; i < trazos.size(); i++) {
            if(trazos.get(i) != null) {
                paint.setColor(trazos.get(i).getColor());
                canvas.drawPath(trazos.get(i), paint);
            }
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);

        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                Trazo trazo = new Trazo();
                trazo.moveTo(event.getX(pointerIndex), event.getY(pointerIndex));
                trazo.setColor(Color.rgb(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                trazos.put(pointerId,trazo);
                break;
            case MotionEvent.ACTION_MOVE:
                for(int i = 0; i < event.getPointerCount(); i++) {
                    if (trazos.get(event.getPointerId(i)) != null) {
                        trazos.get(event.getPointerId(i)).lineTo(event.getX(i), event.getY(i));
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                trazos.remove(pointerId);
                break;
        }
        this.invalidate();
        return true;
    }

}
