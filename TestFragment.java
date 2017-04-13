package com.example.root.drawertabtest;


import android.app.Activity;
import android.content.DialogInterface;
import android.database.SQLException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends ListFragment implements AdapterView.OnItemClickListener, FloatingActionButton.OnClickListener{
    private ListAdapter mListAdapter;
    private int mTabNum;
    
    private List<String> mNames;
    private int mListItemNum;
    public TestFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(){
        return new TestFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return
        View v = inflater.inflate(R.layout.fragment_blank, container, false);
        FloatingActionButton floatingActionButton = (FloatingActionButton)v.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),
                        "Button is clicked", Toast.LENGTH_LONG).show();
            }
        });
        return v;

        //return super.onCreateView(inflater,container,savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();


        mListItemNum = 5;
        //List<String> strArr = new ArrayList<>();
        mNames = new ArrayList<>();
        HangboardData.getNames(mNames);
        ArrayAdapter<String> itemAdapter= new ArrayAdapter<>(getActivity(), R.layout.list_view_layout, mNames);
        //ListView listView = (ListView) v.findViewById(R.id.list);
        setListAdapter(itemAdapter);
        getListView().setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        //Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setNeutralButton("add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which){
                HangboardData.addHangboardData("list_item_" + Integer.toString(mListItemNum++), mListItemNum, mListItemNum);
                mNames = new ArrayList<>();
                HangboardData.getNames(mNames);
                ArrayAdapter<String> itemAdapter= new ArrayAdapter<>(getActivity(), R.layout.list_view_layout, mNames);
                //ListView listView = (ListView) getView().findViewById(R.id.test_list);
                setListAdapter(itemAdapter);  
            }
        });
        alertDialogBuilder.setPositiveButton("delete", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog,int which){
                HangboardData.deleteDataByName(mNames.get(position));
                mNames = new ArrayList<>();
                HangboardData.getNames(mNames);
                ArrayAdapter<String> itemAdapter= new ArrayAdapter<>(getActivity(), R.layout.list_view_layout, mNames);
                //ListView listView = (ListView) getView().findViewById(R.id.test_list);
                setListAdapter(itemAdapter);
                //getListView().setOnItemClickListener(this);
            }
        });

        alertDialogBuilder.setNegativeButton("edit", null);
        AlertDialog ad = alertDialogBuilder.create();
        ad.show();
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(),
                "Button is clicked", Toast.LENGTH_LONG).show();
    }
}
