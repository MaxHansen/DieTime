package com.tictactoe.max.dietime.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tictactoe.max.dietime.R;
import com.tictactoe.max.dietime.models.implement.DieCup;
import com.tictactoe.max.dietime.models.implement.DieLog;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    HistoryAdapter adapter;
    DieLog dieLog;

    Button btnClear;
    Button btnBack;
    LinearLayout pnlHistory;
    ListView lstResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getWidgets();
        setUpButtons();

        dieLog = DieLog.getInstance();
        adapter = new HistoryAdapter(this, R.layout.die_cell, dieLog.getAll());

    }

    private void getWidgets(){
        //------------buttons---------
        btnBack = (Button)findViewById(R.id.btnBack);
        btnClear = (Button)findViewById(R.id.btnClear);
        //------------panels----------
        pnlHistory = (LinearLayout)findViewById(R.id.pnlHistory);
        //------------ListViews----------
        lstResult = (ListView)findViewById(R.id.lstResult);
    }

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

    private void goBack(){
        Intent intent = new Intent();
        intent.setClass(this, DieActivity.class);
        startActivity(intent);
    }

    private void clear(){
        dieLog.clear();
    }

    class HistoryAdapter extends ArrayAdapter<DieCup>{
        private ArrayList<DieCup> theDieCups;

        public HistoryAdapter(Context context, int resource, ArrayList<DieCup> theDieCups) {
            super(context, resource);
            this.theDieCups = theDieCups;
        }

        @Override
        public View getView(int position, View v, ViewGroup parent){

            DieCup dieCup = theDieCups.get(position);

            TextView date = (TextView)findViewById(R.id.txtDate);
            date.setText(dieCup.getDate());
            return v;
        }

    }

}
