package com.example.root.drawertabtest;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.Inflater;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends ListFragment implements AdapterView.OnItemClickListener, FloatingActionButton.OnClickListener{
    private ListAdapter mListAdapter;
    private int mTabNum;
    private static String TEST_PREFS = "test_prefs";
    private List<String> mNames;
   // private int mListItemNum;
    public TestFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(){
        return new TestFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        //return
        //HangboardData.addListener(this);
        View v = inflater.inflate(R.layout.fragment_blank, container, false);
        FloatingActionButton floatingActionButton = (FloatingActionButton)v.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DialogFragment listItemDialogFragment = new NewListItemDialogFragment();
                //listItemDialogFragment.show(getActivity().getSupportFragmentManager(),"test");
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                //alertDialogBuilder.setView(v.findViewById(R.id.));
                LayoutInflater inflater = getLayoutInflater(savedInstanceState);
                View view = inflater.inflate(R.layout.alert_dialog_layout, null);
                final EditText editText = (EditText)view.findViewById(R.id.TestText);
                alertDialogBuilder.setView(view);
                alertDialogBuilder.setTitle("New Hold");
                alertDialogBuilder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //HangboardData.addHangboardData(editText.getText().toString(), 0,0);
                        mNames.add(editText.getText().toString());
                        SharedPreferences sharedPrefs = getActivity().getSharedPreferences(TEST_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        editor.clear();
                        StringBuilder sb = new StringBuilder();
                        for (String name:mNames) {
                            sb.append(name).append(",");
                        }

                        editor.clear();
                        editor.putString("Holds", sb.toString());
                        editor.commit();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

                alertDialog.show();
                //editText.setFocusable(true);

            }
        });
        return v;

        //return super.onCreateView(inflater,container,savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();

        SharedPreferences sharedPreferences = activity.getSharedPreferences(TEST_PREFS, MODE_PRIVATE);
        String[] tempArray = sharedPreferences.getString("Holds", "empty").split(",");
        if(tempArray[0]=="empty"){
            Toast.makeText(getActivity(),
                    "Shared Prefs Empty", Toast.LENGTH_LONG).show();
            mNames = new ArrayList<>();
            mNames.add("Jug");
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Holds", mNames.get(0));
            editor.commit();
        }
        else{
            mNames = new ArrayList<String>(Arrays.asList(tempArray));
            Toast.makeText(getActivity(),
                    sharedPreferences.getString("Holds", "empty"), Toast.LENGTH_LONG).show();
        }
//   mListItemNum  = 5;
        //List<String> strArr = new ArrayList<>();


        //HangboardData.getNames(mNames);
        ArrayAdapter<String> itemAdapter= new ArrayAdapter<>(getActivity(), R.layout.list_view_layout, mNames);
        //ListView listView = (ListView) v.findViewById(R.id.list);
        setListAdapter(itemAdapter);
        getListView().setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
        //Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setNeutralButton("add data", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which){
                //HangboardData.addHangboardData("list_item_" + Integer.toString(mListItemNum++), mListItemNum, mListItemNum);
                AlertDialog.Builder aDBuilder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater(null);
                View v = inflater.inflate(R.layout.x_y_view, null);
                final EditText xData = (EditText)v.findViewById(R.id.TestText);
                final EditText yData = (EditText)v.findViewById(R.id.TestText2);
                aDBuilder.setView(v);
                aDBuilder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int x = Integer.valueOf(xData.getText().toString());
                        int y = Integer.valueOf(yData.getText().toString());
                        HangboardData.addHangboardData(mNames.get(position),x,y);
                    }
                });
                AlertDialog ad = aDBuilder.create();
                ad.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                ad.show();
            }
        });
        alertDialogBuilder.setPositiveButton("delete", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog,int which){
                HangboardData.deleteDataByName(mNames.get(position));
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

    /*@Override
    public void onHangboardDataUpdate() {
        mNames = new ArrayList<>();
        HangboardData.getNames(mNames);
        ArrayAdapter<String> itemAdapter= new ArrayAdapter<>(getActivity(), R.layout.list_view_layout, mNames);
        //ListView listView = (ListView) getView().findViewById(R.id.test_list);
        setListAdapter(itemAdapter);
    }*/
}
