package com.lab.olveczkylabsignatures;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import java.util.ArrayList;
import java.util.List;

public class SignatureView extends View implements OnTouchListener {
    // create data set
    List<Data> set = new ArrayList<Data>();
    Paint paint = new Paint();
    private Path path;

    public SignatureView(Context context) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);
        
        this.setOnTouchListener(this);

        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(5f);

        this.path = new Path();
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
        canvas.drawPoint(200, 500, paint);
        canvas.drawPoint(200, 100, paint);
    }

    public boolean onTouch(View view, MotionEvent event) {

        // make point data for collection
        Data data = new Data();
        data.x = event.getX();
        data.y = event.getY();
        
        // get time
        data.millisecond = System.currentTimeMillis();
        
        // add compiled data to dataset
        set.add(data);
        
        // draw path of signature
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(data.x, data.y);
                return true;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                path.lineTo(data.x, data.y);
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }
}

//specify custom Data class
class Data {
    float x, y;
    long millisecond;
}
