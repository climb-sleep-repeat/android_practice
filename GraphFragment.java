package com.example.root.drawertabtest;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.drawertabtest.BlankFragment;
import com.example.root.drawertabtest.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


/**
 * A simple {@link Fragment} subclass.
 */
public class GraphFragment extends BlankFragment {
    LineGraphSeries<DataPoint> mSeries;
    int mTabNum;
    public GraphFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_graph, null);
        //TextView textView = (TextView)v.findViewById(R.id.graph_text);
        //textView.setText("I'm here!");
        GraphView graphView = (GraphView)v.findViewById(R.id.graph);

        //graphView.setBackgroundColor(Color.RED);
        double x = 0;
        double y = 0;
        mSeries = new LineGraphSeries<DataPoint>();
        mSeries.setDrawBackground(true);
        mSeries.setBackgroundColor(Color.BLUE);
        for(int i = 0;i<100;i++) {
            x = x + 0.1;
            y = Math.sin(x);
            mSeries.appendData(new DataPoint(x, y), true, 100);
        }
        graphView.addSeries(mSeries);
        return v;
    }

}
