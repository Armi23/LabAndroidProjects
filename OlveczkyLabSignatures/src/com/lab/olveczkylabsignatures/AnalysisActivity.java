
package com.lab.olveczkylabsignatures;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import java.util.List;

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
    
    // Plot X vs. Time line graph
    public void graphXvsTime(View view){
        Utilities utils = new Utilities();
        
        List<List<Point>> sigset = utils.buildSet(1,1,1);
        List<Point> signatureset = sigset.get(0);
        int len = signatureset.size();
        GraphViewData[] data = new GraphViewData[len];
        for (int i=0; i<len; i++) {
            data[i] = new GraphViewData(signatureset.get(i).time,signatureset.get(i).x);  
        }
        
        GraphViewSeries xSeries = new GraphViewSeries(data);
        
        GraphView graphView = new LineGraphView(
                this // context
                , "X vs. Time" // heading
        );
        graphView.addSeries(xSeries); // data
        
        LinearLayout layout = (LinearLayout) findViewById(R.id.graph2);
        layout.addView(graphView);
    }

}
