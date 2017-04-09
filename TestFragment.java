package com.example.root.drawertabtest;


import android.app.Activity;
import android.database.SQLException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
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
        //View v = inflater.inflate(R.layout.fragment_blank, null);
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

        mData.addHangboardData("bcd", 2,3);

        List<String> strArr = new ArrayList<>();

        mData.getNames(strArr);

        ArrayAdapter<String> itemAdapter= new ArrayAdapter<>(activity, R.layout.list_view_layout, strArr);
        //ListView listView = (ListView) getView().findViewById(R.id.test_list);
        setListAdapter(itemAdapter);
        getListView().setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();

    }
}
