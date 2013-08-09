
package com.lab.olveczkylabsignatures;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utilities extends Activity {
    
    List<List<Point>> sigset = new ArrayList<List<Point>>();

    public List<List<Point>> buildSet(int dir,int start, int end) {
        // make file    
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/RatSigs/" + dir);
        String fname;
        File file;
        

        for (int n = start; n <= end; n++) {
            ArrayList<Point> pset = new ArrayList<Point>();
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
                    
                    Point point = new Point();
                    
                    // Only grab X and Y coordinates, and we know we have both points after getting Y
                    while ((line = in.readLine()) != null) {
                     
                        if (line.charAt(0) == 'X') {
                            point.x = Float.valueOf(line.substring(2));
                        }
                        
                        else if (line.charAt(0) == 'Y') {
                            point.y = Float.valueOf(line.substring(2));
                        }
                           
                        else if (line.charAt(0) == 'T') {
                            point.time = Float.valueOf(line.substring(2));
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
        return sigset;
    }
}

//specify custom Data class
class Point {
float x, y, time;
}
