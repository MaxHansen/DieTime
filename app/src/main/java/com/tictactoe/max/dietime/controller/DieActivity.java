package com.tictactoe.max.dietime.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.tictactoe.max.dietime.R;

public class DieActivity extends AppCompatActivity {

    Button btnHistory;
    Button btnRoll;
    LinearLayout pnlDie;
    Spinner spnNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_die);

        getWidgets();
        setUpSpnNumbers();
        setUpButtons();
    }

    /**
     * set's up the spnNumbers
     */
    private void setUpSpnNumbers() {
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_list_item_checked);
        spnNumbers.setAdapter(adapter);
    }

    /**
     * Collects the widgets, and sets them to the instance variables.
     */
    private void getWidgets(){
        //------------buttons---------
        btnHistory = (Button) findViewById(R.id.btnHistory);
        btnRoll = (Button) findViewById(R.id.btnRoll);
        //----------------------------

        //------------panels----------
        pnlDie = (LinearLayout) findViewById(R.id.pnlDie);
        //----------------------------

        //------------spinners---------
        spnNumbers = (Spinner) findViewById(R.id.spnNumbers);
        //----------------------------
    }

    private void setUpButtons(){
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHistoryClick();
            }
        });
    }

    private void onHistoryClick(){
        Intent intent = new Intent();
        intent.setClass(this, HistoryActivity.class);
        startActivity(intent);
    }

}
