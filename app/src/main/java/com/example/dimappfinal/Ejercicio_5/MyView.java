package com.example.dimappfinal.Ejercicio_5;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.example.dimappfinal.Trazo;

import java.util.ArrayList;
import java.util.Random;

import static android.view.MotionEvent.INVALID_POINTER_ID;


public class MyView extends View implements  GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener, ScaleGestureDetector.OnScaleGestureListener {

    // Almacena las propiedades graficas de dibujo de la linea
    Paint paint = new Paint();
    // Almacenan la posicion inicial y final de la linea
    float prevX, prevY;
    // HashMap para almacenar las pulsaciones
    ArrayList<Trazo> trazos = new ArrayList<>();
    // Gesture detector
    private GestureDetector gDetector;
    private ScaleGestureDetector sDetector;
    private float DIMENSION = 100;
    // Active pointer identifier
    private int activePointerId = INVALID_POINTER_ID;
    private boolean encontrado;
    private Trazo trazoScale = new Trazo();
    private float mScaleFactor = 1;
    private int color = Color.BLACK;
    Random random = new Random();

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Initialize
        gDetector = new GestureDetector(context,this);
        gDetector.setOnDoubleTapListener(this);
        sDetector = new ScaleGestureDetector(context, this);
        sDetector.setQuickScaleEnabled(true);
        // Establish line graphic properties
        paint.setAntiAlias(true);
        paint.setStrokeWidth(6f);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i = 0; i < trazos.size(); i++){
            paint.setColor(trazos.get(i).getColor());

            canvas.drawPath(trazos.get(i),paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gDetector.onTouchEvent(event);
        this.sDetector.onTouchEvent(event);
        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);

        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                prevX = event.getX(pointerIndex);
                prevY = event.getY(pointerIndex);
                activePointerId = event.getPointerId(0);
                break;
            case MotionEvent.ACTION_MOVE: {
                try {
                    pointerIndex = event.findPointerIndex(activePointerId);
                    prevX = event.getX(pointerIndex);
                    prevY = event.getY(pointerIndex);
                }
                catch(Exception e){}
                this.invalidate();
                break;
            }
            case MotionEvent.ACTION_POINTER_UP:
                if (pointerId == activePointerId) {
                    prevX = event.getX(pointerIndex);
                    prevY = event.getY(pointerIndex);
                    activePointerId = event.getPointerId(pointerIndex);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                activePointerId = INVALID_POINTER_ID;
                break;
            }
        }
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Trazo trazo = new Trazo();
        this.color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        trazo.setColor(color);
        trazo.moveTo(prevX,prevY);
        trazo.setPosActualX(prevX);
        trazo.setPosActualY(prevY);
        trazo.setDimension(1);
        trazo.addRoundRect( prevX+DIMENSION, prevY+DIMENSION, prevX-DIMENSION, prevY-DIMENSION, 20,20,Path.Direction.CW);

        // Add the path to the list that will be drawed
        trazos.add(trazo);
        this.invalidate();
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
        Trazo trazo = new Trazo();
        int i;
        encontrado = false;
        for(i = 0; i < trazos.size(); i++){
            trazo = trazos.get(i);
            if(distanceX+prevX>trazo.getPosActualX()-(DIMENSION*trazo.getDimension())
                    && distanceX+prevX<trazo.getPosActualX()+(DIMENSION*trazo.getDimension())
                    && distanceY+prevY<trazo.getPosActualY()+(DIMENSION*trazo.getDimension())
                    && distanceY+prevY>trazo.getPosActualY()-(DIMENSION*trazo.getDimension())){
                encontrado = true;
                break;
            }
        }
        if(encontrado) {
            int color = trazo.getColor();
            trazo.rewind();
            trazo.moveTo(prevX + distanceX, prevY + distanceY);
            trazo.setPosActualX(prevX + distanceX);
            trazo.setPosActualY(prevY + distanceY);
            trazo.setColor(color);
            trazo.addRoundRect(prevX + trazo.getDimension()*DIMENSION, prevY + trazo.getDimension()*DIMENSION, prevX -  trazo.getDimension()*DIMENSION, prevY -  trazo.getDimension()*DIMENSION, 20, 20, Path.Direction.CW);
            trazos.set(i, trazo);
        }
        this.invalidate();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        mScaleFactor *= detector.getScaleFactor(); // scale change since previous event
        // Don't let the object get too small or too large.
        mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
        if(trazoScale!=null){
            trazoScale.rewind();
            trazoScale.setDimension(mScaleFactor);
            trazoScale.addRoundRect(prevX + trazoScale.getDimension()*DIMENSION, prevY + trazoScale.getDimension()*DIMENSION, prevX -  trazoScale.getDimension()*DIMENSION, prevY -  trazoScale.getDimension()*DIMENSION, 20, 20, Path.Direction.CW);
        }

        this.invalidate();
        return true;
    }


    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        Trazo trazo = new Trazo();
        int i = 0;
        encontrado = false;
        for(i = 0; i < trazos.size(); i++){
            trazo = trazos.get(i);
            if(prevX>trazo.getPosActualX()-(DIMENSION*trazo.getDimension())
                    && prevX<trazo.getPosActualX()+(DIMENSION*trazo.getDimension())
                    && prevY<trazo.getPosActualY()+(DIMENSION*trazo.getDimension())
                    && prevY>trazo.getPosActualY()-(DIMENSION*trazo.getDimension())){
                encontrado = true;
                break;
            }
        }
        if(encontrado) {
            trazoScale = trazo;
            mScaleFactor = trazo.getDimension();
        }
        this.invalidate();
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        trazoScale = null;
        this.invalidate();
    }
}