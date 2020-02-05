package com.example.dimappfinal.Entregable;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.SeekBar;

import com.example.dimappfinal.Trazo;
import com.example.dimappfinal.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MyView extends View implements  GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener, ScaleGestureDetector.OnScaleGestureListener{
    // Generador de numeros aleatorios
    Random rdm = new Random();
    // Almacena las propiedades graficas de dibujo de la linea
    Paint paint = new Paint();
    // Almacenan la posicion inicial y final de la linea
    public static int GROSOR_LINEA = 1;
    int color = Color.BLACK;
    // Lista para guardar las líneas creadas
    public static boolean borrado = false;
    private HashMap<Integer, Trazo> trazos = new HashMap<>();
    private ArrayList<Trazo> lista_trazos = new ArrayList<>();
    SeekBar simpleSeekBar=findViewById(R.id.seekBar2); // initiate the progress bar
    private ScaleGestureDetector sDetector;
    private GestureDetector gDetector;
    boolean onScaleEnabled = false;



    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Establezco las propiedades graficas de dibujo de la linea
        paint.setAntiAlias(true);
        paint.setStrokeWidth(6f);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        sDetector = new ScaleGestureDetector(context,this);
        sDetector.setQuickScaleEnabled(true);
        gDetector = new GestureDetector(context,this);
        gDetector.setOnDoubleTapListener(this);
    }

    public void limpiarTrazos(){
        lista_trazos.clear();
        this.invalidate();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Establezco el color con el que se va a dibujar la l�nea en curso
        paint.setColor(getColor());
        for(int i = 0; i < lista_trazos.size(); i++) {
            if(lista_trazos.get(i) != null && !onScaleEnabled) {
                paint.setColor(lista_trazos.get(i).getColor());
                if(GROSOR_LINEA > 0) paint.setStrokeWidth(lista_trazos.get(i).getGrosor());
                canvas.drawPath(lista_trazos.get(i), paint);

            }
        }
    }

    private int getColor(){
        return Activity6.currentColor;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);
        this.sDetector.onTouchEvent(event);
        this.gDetector.onTouchEvent(event);
        //Si la barra de grosor está activa, evita que se dibujen trazos
        if(onScaleEnabled) return true;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                this.color = getColor();
                Trazo p = new Trazo();
                p.setColor(color);
                p.setGrosor(GROSOR_LINEA);
                p.moveTo(event.getX(pointerIndex), event.getY(pointerIndex));
                trazos.put(pointerId,p);
                break;
            case MotionEvent.ACTION_MOVE:
                for(int i = 0; i < event.getPointerCount(); i++) {
                    if (trazos.get(event.getPointerId(i)) != null) {
                        trazos.get(event.getPointerId(i)).lineTo(event.getX(i), event.getY(i));
                        lista_trazos.add(trazos.get(event.getPointerId(i)));
                    }
                }
                this.invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                break;
        }
        return true;
    }


    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        if ((detector.getScaleFactor() > 1)) {
            GROSOR_LINEA++;
        } else {
            GROSOR_LINEA--;
        }
        GROSOR_LINEA = Math.max(1, Math.min(GROSOR_LINEA, 40));
        Context context = getContext();
        Activity6 host = new Activity6();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                host = (Activity6)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        host.actualizarGrosorSeekBar(GROSOR_LINEA);
        return onScaleEnabled;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return onScaleEnabled;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Context context = getContext();
        Activity6 host = new Activity6();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                host = (Activity6)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        host.visibleSeekBar(!onScaleEnabled,GROSOR_LINEA);
        onScaleEnabled = !onScaleEnabled;
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
