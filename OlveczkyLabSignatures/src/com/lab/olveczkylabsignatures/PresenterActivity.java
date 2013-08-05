
package com.lab.olveczkylabsignatures;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

//import com.example.signaturepresenter.SigBuild;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PresenterActivity extends Activity {

    SigBuild sigbuild;
    int directory;
    int start = 0;
    int end;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "Load Directory", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        
        // make a new menu based on changes
        menu.clear();
        
        // dynamically build directory submenu based on what we have
        Menu directoryMenu = menu.addSubMenu("Load Directory");
        directoryMenu = buildDirectoryMenu(directoryMenu);

        // also dynamically build for the start, end, and individual menus
        Menu startMenu = menu.addSubMenu("Select Start Point");
        Menu endMenu = menu.addSubMenu("Select End Point");
        Menu indMenu = menu.addSubMenu("Select Individual Signature");
        
        List<Menu> returnList = buildSubMenu(startMenu,endMenu,indMenu);
        startMenu = returnList.get(0);
        endMenu = returnList.get(1);
        indMenu = returnList.get(2);
        
        endMenu.add(0, 500, 0, "Final Signature");
        
        // actually make menu
        getMenuInflater().inflate(R.menu.presenter, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Different ranges correspond to different actions
        int id = item.getItemId();      
        
        // change directory and reinitialize start point to 0. End point handled in buildSubMenu
        if (id == R.id.Draw) {
            Intent intent = new Intent(PresenterActivity.this,MainActivity.class);
            PresenterActivity.this.startActivity(intent);            
            return true;
        }
        
        else if (id == R.id.Analysis) {
            Intent intent = new Intent(PresenterActivity.this,AnalysisActivity.class);
            PresenterActivity.this.startActivity(intent);            
            return true;
        }
        
        else if (id >= 100 && id < 200) {
            directory = id % 100;
            start = 0;
            return true;
        }
        
        // change start point
        else if (id >= 200 && id < 300) {
            start = id % 100;
            return true;
        }
        
        // change end point
        else if (id >= 300 && id < 400) {
            end = id % 100;
            return onEndSelected();
        }
        
        // select individual signature
        else if (id >= 400 && id < 500) {
            start = id % 100;
            end = id % 100;
            return onEndSelected();
        }
        
        // show all signatures after the start point. End should have been adjusted by buildSubMenu.
        else if(id == 500){
            return onEndSelected();
        }
        
        else {
            super.onOptionsItemSelected(item);
        }
        
        return false;
        
    }
    
    
    // draws signature based on user inputed values. Can only be reached after an end point is selected
    public boolean onEndSelected(){
        sigbuild = new SigBuild(this,directory,start,end);
        setContentView(sigbuild);
        sigbuild.requestFocus();
        return true;
    }
    
    // function call for dynamic Directory building
    public Menu buildDirectoryMenu(Menu directoryMenu){
        // make initial file
        String root = Environment.getExternalStorageDirectory().toString();
        int n = 1;
        File myDir = new File(root + "/RatSigs/" + n);    
        String fname = "Sig-0.txt";
        File file = new File (myDir, fname);
                
        // add submenu options based on number of directories and increment to next file
        while (file.exists ()) {
            directoryMenu.add(0, 100 + n, 100 + n, "Load directory ID: " + n);
            n = n + 1;
            myDir = new File(root + "/RatSigs/" + n);    
            file = new File (myDir, fname);
        }
        return directoryMenu;
    }
    
    // function call for dynamic building. Takes all of them at the same time to save time
    public List<Menu> buildSubMenu(Menu startMenu, Menu endMenu, Menu indMenu){
        List<Menu> returnList = new ArrayList<Menu>();
        
        // make initial file
        String root = Environment.getExternalStorageDirectory().toString();
        int n = 0;
        File myDir = new File(root + "/RatSigs/" + directory);    
        String fname = "Sig-" + n + ".txt";
        File file = new File (myDir, fname);
        
        // add submenu options based on number of files
        while (file.exists ()) {
            startMenu.add(0, 200 + n, 200 + n, "Start at signature " + n);
            endMenu.add(0, 300 + n, 300 + n, "End at signature " + n);
            indMenu.add(0,400 + n, 400 + n, "Only show signature " + n);
            
            // change end variable in case user wants to display all files
            end = n;
            
            // increment to next file
            n = n + 1;
            fname = "Sig-" + n + ".txt";
            file = new File (myDir, fname);
        }
        
        returnList.add(startMenu);
        returnList.add(endMenu);
        returnList.add(indMenu);
        return  returnList;
    }

}
