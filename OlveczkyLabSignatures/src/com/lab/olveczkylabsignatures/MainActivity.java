
package com.lab.olveczkylabsignatures;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
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

    // holds the view of the page    
    SignatureView sView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // make a new sView, which is blank, and set it as the current view
        sView = new SignatureView(this); 
        setContentView(sView);
        sView.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            // Keep a list of which IDs match which person. Currently limited to 6, (add command to add new person/delete)
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
            case R.id.Presenter:
                // change view to presenter
                Intent intent = new Intent(MainActivity.this, PresenterActivity.class);
                MainActivity.this.startActivity(intent);
                return true;
            case R.id.Analysis:
                // change view to analysis page. This is to test change
                intent = new Intent(MainActivity.this, AnalysisActivity.class);
                MainActivity.this.startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    // After writing signature and selecting export ID, this function will save the information
    public boolean onExport(String id){
        // Retrieve dataset
        List<Data> set = sView.set;

        // find/create directory
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/RatSigs/" + id);    
        myDir.mkdirs();
        int n = 0;
        
        // initialize values for file
        String fname;
        File file;
                
        // make sure we have a new file
        do
        {
            fname = "Sig-"+ n +".txt";
            file = new File (myDir, fname);
            n++;
        }
        while (file.exists());
        
        
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
                    out.write("T:" + set.get(i).millisecond + "\n"); 
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
