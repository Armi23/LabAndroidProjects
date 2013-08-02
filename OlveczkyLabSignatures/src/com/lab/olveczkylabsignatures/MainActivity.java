
package com.lab.olveczkylabsignatures;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MainActivity extends Activity {

    SignatureView sView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sView = new SignatureView(this);
        setContentView(sView);
        sView.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case R.id.Export:
                return true;
            case R.id.Clear:
                return setClear();
            case R.id.item1:
                return onExport("1");
            case R.id.item2:
                return onExport("2");
            case R.id.item3:
                return onExport("3");
            case R.id.item4:
                return onExport("4");
            case R.id.item5:
                return onExport("5");
            case R.id.item6:
                return onExport("6");
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    public boolean onExport(String str){
        // Retrieve dataset
        List<Data> set = sView.set;

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
        Toast.makeText(MainActivity.this,"Made " + myDir + "/" + fname, Toast.LENGTH_SHORT).show(); 
        
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
                Toast.makeText(MainActivity.this,"File exported", Toast.LENGTH_LONG).show();  
            }
        } catch (IOException e) {
            Log.e("TAG", "Could not write file " + e.getMessage());
        }
        
        return setClear();
    }
    
    public boolean setClear(){
        sView = new SignatureView(this);
        setContentView(sView);
        sView.requestFocus();
        return true;
    }
}
