package com.lab.olveczkylabsignatures;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("ViewConstructor")
public class SigBuild extends View {
    // create data set

    List<List<Point>> sigset = new ArrayList<List<Point>>();
    
    Paint paint = new Paint();
    Point point;

    public SigBuild(Context context, int dir, int start, int end) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);

        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(5f);
        
        Utilities utils = new Utilities();
        sigset = utils.buildSet(dir,start,end);
    }

    @Override
    public void onDraw(Canvas canvas) {
        for (int j = 0; j < sigset.size(); j++) {
            List<Point> pset = sigset.get(j);
            for (int i = 1; i < pset.size(); i ++){
                canvas.drawLine(pset.get(i-1).x, pset.get(i-1).y, pset.get(i).x, pset.get(i).y, paint);
            }
        }
    }
}