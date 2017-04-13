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

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GraphFragment extends BlankFragment implements HangboardData.HangboardDataListener{

    int mTabNumes;
    GraphView mGraphView;
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
        mGraphView = (GraphView)v.findViewById(R.id.graph);
        HangboardData.addListener(this);
        updateGraph();
        return v;
    }

    @Override
    public void onHangboardDataUpdate() {
        updateGraph();
    }

    private void updateGraph(){
        if(!mGraphView.getSeries().isEmpty())
                mGraphView.removeAllSeries();

        List<String> names = new ArrayList<>();
        HangboardData.getNames(names);
        for(int i = 0; i<HangboardData.getNumberOfNames();i++){
            int size = HangboardData.getHangboardDataSizeForName(names.get(i));
            int[] y = new int[size];
            int[] x = new int[size];
            HangboardData.getHangboardData(names.get(i), x, y);

            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
            series.setTitle(names.get(i));
            series.setDrawBackground(true);
//            series.setBackgroundColor(Color.BLUE);
            for(int j = 0;j<size;j++) {
                series.appendData(new DataPoint(x[j], y[j]), true, size);
            }
            mGraphView.addSeries(series);
        }
    }
}

