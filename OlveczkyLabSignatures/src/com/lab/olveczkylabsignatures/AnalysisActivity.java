
package com.lab.olveczkylabsignatures;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class AnalysisActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // clear old menu
        menu.clear();
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.analysis, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Presenter:
                Intent intent = new Intent(AnalysisActivity.this,PresenterActivity.class);
                AnalysisActivity.this.startActivity(intent);
                return true;
            case R.id.Draw:
                Intent intentDraw = new Intent(AnalysisActivity.this,MainActivity.class);
                AnalysisActivity.this.startActivity(intentDraw);
                return true;
            default:
                return false;                   
        }
    }

}
