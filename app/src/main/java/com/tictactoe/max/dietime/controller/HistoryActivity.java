package com.tictactoe.max.dietime.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tictactoe.max.dietime.R;
import com.tictactoe.max.dietime.models.abstraction.IDieCup;
import com.tictactoe.max.dietime.models.abstraction.IDieLog;
import com.tictactoe.max.dietime.models.implement.Dice;
import com.tictactoe.max.dietime.models.implement.DieCup;
import com.tictactoe.max.dietime.models.implement.DieLog;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    //-------------------constants---------------
    private final String TAG = "HistoryActivity";
    //----------------------------------------------

    //----------------variables---------------------
    private ArrayList<ImageView> dieImages;
    private HistoryAdapter adapter;
    private IDieLog dieLog;
    //----------------------------------------------

    //---------------Views--------------------------
    Button btnClear;
    Button btnBack;
    LinearLayout pnlHistory;
    ListView lstResult;
    LinearLayout pnlResult;
    //----------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        dieImages = new ArrayList<>();

        getWidgets();
        setUpButtons();

        dieLog = DieLog.getInstance();
        adapter = new HistoryAdapter(this, R.layout.die_cell, dieLog.getAll());
        lstResult.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        //fa.notifyDataSetChanged();
    }

    private void getWidgets(){
        //------------buttons---------
        btnBack = (Button)findViewById(R.id.btnBack);
        btnClear = (Button)findViewById(R.id.btnClear);
        //------------panels----------
        pnlHistory = (LinearLayout)findViewById(R.id.pnlHistory);
        pnlResult = (LinearLayout)findViewById(R.id.pnlResult);
        //------------ListViews----------
        lstResult = (ListView)findViewById(R.id.lstResult);
    }

    /**
     * This method will set up the buttons and listeners
     */
    private void setUpButtons(){
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    /**
     * This should go back to the DieActivity
     */
    private void goBack(){
        Intent intent = new Intent();
        intent.setClass(this, DieActivity.class);
        Log.d(TAG, "Back button is clicked..");
        startActivity(intent);
        Log.d(TAG, "Back to DieActivity..");
    }

    /**
     * This should clear the history log
     */
    private void clear(){
        dieLog.clear();
        Log.d(TAG, "Clear Button is clicked");
    }


    class HistoryAdapter extends ArrayAdapter<DieCup>{
        private ArrayList<DieCup> theDieCups;

        private final int[] colours = {
                Color.parseColor("#AAAAAA"),
                Color.parseColor("#EEEEEE")
        };


        public HistoryAdapter(Context context, int resource, ArrayList<DieCup> theDieCups) {
            super(context, resource, theDieCups);
            this.theDieCups = theDieCups;
        }

        @Override
        public View getView(int position, View v, ViewGroup parent){
            if (v == null) {
                LayoutInflater li = (LayoutInflater) getContext().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                v = li.inflate(R.layout.die_cell, null);
                Log.d("LIST", "Position: " + position + " View created");
            }
            else
                Log.d("LIST", "Position: " + position + " View Reused");

            DieCup dieCup = theDieCups.get(position);

            v.setBackgroundColor(colours[position % colours.length]);
            //------------Views-------------
            TextView date = (TextView)findViewById(R.id.txtDate);
            //------------------------------



            //---------set Views--------------
            date.setText(dieCup.getDate());

            return v;
        }




    }
}