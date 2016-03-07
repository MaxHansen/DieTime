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
        finish();
    }

    /**
     * This should clear the history log
     */
    private void clear(){
        dieLog.clear();
        adapter = new HistoryAdapter(this, R.layout.die_cell, dieLog.getAll());
        lstResult.setAdapter(adapter);
        Log.d(TAG, "Clear Button is clicked");
    }


    class HistoryAdapter extends ArrayAdapter<DieCup>{

        private final int INITIAL_CHILD_AMOUNT = 1;
        public HistoryAdapter(Context context, int resource, ArrayList<DieCup> theDieCups) {
            super(context, resource, theDieCups);
        }

        private final int[] colours = {
                Color.parseColor("#AAAAAA"),
                Color.parseColor("#EEEEEE")
        };

        private synchronized void addImageToLayout(LinearLayout layout, Context context, ArrayList<Integer> dieImages) {
            ImageView view = new ImageView(context);
            int imageId = View.generateViewId();
            dieImages.add(imageId);
            view.setId(imageId);
            layout.addView(view);
            view.setImageResource(R.drawable.die2);
        }

        private synchronized void setDieImages(DieCup dieCup,View view, ArrayList<Integer> dieImages) {
            int dieNumber = 0;
            Dice[] die = (Dice[]) dieCup.getAll();
            for(int id : dieImages){
                ImageView img = (ImageView) view.findViewById(id);
                switch (die[dieNumber].getFace()){
                    case 1:
                        img.setImageResource(R.drawable.die1);
                        break;
                    case 2:
                        img.setImageResource(R.drawable.die2);
                        break;
                    case 3:
                        img.setImageResource(R.drawable.die3);
            break;
            case 4:
            img.setImageResource(R.drawable.die4);
            break;
            case 5:
            img.setImageResource(R.drawable.die5);
            break;
            case 6:
            img.setImageResource(R.drawable.die6);
            break;
        }
        dieNumber++;
    }
        }

        private synchronized void setUpImageViews(DieCup dieCup, Context context, LinearLayout dieRow, ArrayList<Integer> dieImages){
            Log.d(TAG, "row added");
            for (int column = 0; column < dieCup.getAll().length; column++) {
                addImageToLayout(dieRow, context,dieImages);
                Log.d(TAG, "image view added");
            }
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

            LinearLayout veiw = (LinearLayout)v.findViewById(R.id.pnlResult);
            veiw.removeAllViews();

            ArrayList<Integer> dieImages = new ArrayList<>();
            DieCup dieCup = getItem(position);

            v.setBackgroundColor(colours[position % colours.length]);
            //------------Views-------------
            TextView date = (TextView)findViewById(R.id.txtDate);
            //------------------------------
            setUpImageViews(dieCup, getContext(),veiw,dieImages);
            setDieImages(dieCup,veiw,dieImages);

            //---------set Views--------------
            //date.setText(dieCup.getDate());

            return v;
        }




    }
}
