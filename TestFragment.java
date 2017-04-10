package com.example.root.drawertabtest;


import android.app.Activity;
import android.content.DialogInterface;
import android.database.SQLException;
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
public class TestFragment extends ListFragment implements AdapterView.OnItemClickListener{
    private ListAdapter mListAdapter;
    private int mTabNum;
    private HangboardData mData;
    private List<String> mNames;
    public TestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    public static Fragment newInstance(){
        return new TestFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_blank, container, false);
       // setListShown(false);
        //return v;
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();

        mData = new HangboardData(activity);
        try{
            mData.init();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

        mData.addHangboardData("list_item_1", 1,4);
        mData.addHangboardData("list_item_2", 2,3);
        mData.addHangboardData("list_item_3", 3,2);
        mData.addHangboardData("list_item_4", 4,1);

        //List<String> strArr = new ArrayList<>();
        mNames = new ArrayList<>();
        mData.getNames(mNames);

        ArrayAdapter<String> itemAdapter= new ArrayAdapter<>(activity, R.layout.list_view_layout, mNames);
        //ListView listView = (ListView) getView().findViewById(R.id.test_list);
        setListAdapter(itemAdapter);
        getListView().setOnItemClickListener(this);
        FloatingActionButton button = (FloatingActionButton)getView().findViewById(R.id.button);
        //button.show();
        /*button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){

            }
        }
        );*/
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        //Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setPositiveButton("delete", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog,int which){
                mData.deleteDataByName(mNames.get(position));
                mNames = new ArrayList<>();
                mData.getNames(mNames);
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
}
