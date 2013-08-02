package com.example.draw;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Draw extends MainActivity {
    
    DrawView drawView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        drawView = new DrawView(this);
        setContentView(drawView);
        drawView.requestFocus();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.Export:
                return true;
            case R.id.Armi:
                onExport("Armi");
                return true;
            case R.id.Risa:
                onExport("Risa");
                return true;
            case R.id.Bence:
                onExport("Bence");
                return true;
            case R.id.Clear:
                drawView = new DrawView(this);
                setContentView(drawView);
                drawView.requestFocus();
                return true;
            default: 
                return super.onOptionsItemSelected(item);
        }
    }
    
    public boolean onExport (String str) {
        // Retrieve dataset
        List<Data> set = drawView.set;

        // make file
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/RatSigs/" + str);    
        myDir.mkdirs();
        int n = 0;
        String fname = "Sig-"+ n +".txt";
        File file = new File (myDir, fname);
        
        // check if unique and fix if not
        if (file.exists ()) {
            for (int i = 0;file.exists (); i++)  {
                fname = "Sig-"+ i +".txt";
                file = new File (myDir, fname); 
            }
        }
        
        // Show file name and location as a test
        Toast.makeText(Draw.this,"Made " + myDir + "/" + fname, Toast.LENGTH_SHORT).show(); 
        
        // write to file
        try {
            if (myDir.canWrite()) {
                
                // set up
                FileWriter filewriter = new FileWriter(file);
                BufferedWriter out = new BufferedWriter(filewriter);
                
                // add data set information
                int setleng = set.size();
                
                for (int i = 0; i < setleng; i++) {
                    out.write("D:" + i + "\n");
                    out.write("X:" + set.get(i).x  + "\n");
                    out.write("Y:" + set.get(i).y  + "\n");
                    out.write("H:" + set.get(i).hour + "\n");
                    out.write("M:" + set.get(i).minute + "\n");
                    out.write("S:" + set.get(i).second + "\n"); 
                    out.write("N:" + set.get(i).millisecond + "\n"); 
                    out.write("R" + "\n"); 
                }
                
                out.write("F");
                // close when done and inform user
                out.close();
                Toast.makeText(Draw.this,"File exported", Toast.LENGTH_LONG).show();  
            }
        } catch (IOException e) {
            Log.e("TAG", "Could not write file " + e.getMessage());
        }
        return true;
    }
}