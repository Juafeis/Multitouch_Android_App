package com.example.dimappfinal.Ejercicio_4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.dimappfinal.Trazo;

import java.util.HashMap;
import java.util.Random;

public class MyView extends View {
    // Generador de n�meros aleatorios
    Random random = new Random();
    // Almacena las propiedades gr�ficas de dibujo de la l�nea
    Paint paint = new Paint();
    // Almacenan la posici�n inicial y final de la l�nea
    int color = Color.BLACK;
    // Almacenan la posici�n inicial y final de la l�nea
    float newX, newY;
    private HashMap<Integer, Trazo> trazos = new HashMap<Integer, Trazo>();
    private Trazo vDireccion = new Trazo();

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Establezco las propiedades gr�ficas de dibujo de la l�nea
        paint.setAntiAlias(true);
        paint.setStrokeWidth(6f);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);


    }

    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        for(int i = 0; i < trazos.size(); i++) {
            if (trazos.get(i) != null) {
                paint.setColor(trazos.get(i).getColor());
                canvas.drawPath(vDireccion, paint);
                canvas.drawPath(trazos.get(i), paint);
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                this.color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
                Trazo trazo = new Trazo();
                trazo.moveTo(event.getX(pointerIndex), event.getY(pointerIndex));
                trazo.setColor(color);
                trazos.put(pointerId, trazo);
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < event.getPointerCount(); i++) {
                    if ( trazos.get(event.getPointerId(i)) != null) {
                        trazos.get(event.getPointerId(i)).reset();
                        trazos.get(event.getPointerId(i)).addCircle(event.getX(i),event.getY(i),100,Path.Direction.CW);
                        if(i == 2) {
                            vDireccion.reset();
                            vDireccion.moveTo((((event.getX(1))+(event.getX(0)))/2), ((event.getY(1)) + (event.getY(0)))/2);
                            newX = event.getX(2) - (((event.getX(1))+(event.getX(0)))/2);
                            newY = event.getY(2) - (((event.getY(1))+(event.getY(0)))/2);
                            vDireccion.lineTo((event.getX(2) + newX),(event.getY(2) + newY));
                        }
                    }
                }
                this.invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                trazos.remove(pointerId);
                vDireccion.reset();
                break;
        }
        this.invalidate();
        return true;
    }


}
