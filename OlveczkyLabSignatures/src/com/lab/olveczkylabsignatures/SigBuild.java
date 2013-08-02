package com.lab.olveczkylabsignatures;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.util.Log;
import android.view.View;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("ViewConstructor")
public class SigBuild extends View {
    // create data set
    List<Point> pset;
    List<List<Point>> sigset = new ArrayList<List<Point>>();
    
    Paint paint = new Paint();
    Point point;

    public SigBuild(Context context, int str, int start, int end) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);

        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(5f);
        buildSet(str,start,end);
    }

    @Override
    public void onDraw(Canvas canvas) {
        for (int j = 0; j < sigset.size(); j++) {
            pset = sigset.get(j);
            for (int i = 1; i < pset.size(); i ++){
                canvas.drawLine(pset.get(i-1).x, pset.get(i-1).y, pset.get(i).x, pset.get(i).y, paint);
            }
        }
    }
    
    public boolean buildSet(int str,int start, int end) {
        // make file    
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/RatSigs/" + str);
        String fname;
        File file;
        

        for (int n = start; n <= end; n++) {
            pset = new ArrayList<Point>();
            fname = "Sig-"+ n +".txt";
            file = new File (myDir, fname);
            
            // write to file
            try {
                if (myDir.canRead()) {
                    
                    // set up
                    FileReader filereader = new FileReader(file);
                    BufferedReader in = new BufferedReader(filereader);
                    
                    // read in line
                    String line = null;
                    
                    point = new Point();
                    
                    // Only grab X and Y coordinates, and we know we have both points after getting Y
                    while ((line = in.readLine()) != null) {
                     
                        if (line.charAt(0) == 'X') {
                            point.x = Float.valueOf(line.substring(2));
                        }
                        
                        else if (line.charAt(0) == 'Y') {
                            point.y = Float.valueOf(line.substring(2));
                            pset.add(point);
                            point = new Point();
                        }
                           
                    }
                    
                    sigset.add(pset);
                    // close when done and inform user
                    in.close();
                    
            }
        } 
        
        catch (IOException e) {
            Log.e("TAG", "Could not read file " + e.getMessage());
        }
        
        }
        
        return true;
    }

}

//specify custom Data class
class Point {
  float x, y;
}