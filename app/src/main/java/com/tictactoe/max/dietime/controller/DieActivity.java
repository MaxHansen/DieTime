package com.tictactoe.max.dietime.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.tictactoe.max.dietime.R;
import com.tictactoe.max.dietime.models.abstraction.IDieCup;
import com.tictactoe.max.dietime.models.abstraction.IRoll;
import com.tictactoe.max.dietime.models.implement.Dice;
import com.tictactoe.max.dietime.models.implement.DieCup;

import java.util.ArrayList;

public class DieActivity extends AppCompatActivity {
    //-------------------constants---------------
    private final int MINIMUM_NUMBER_OF_DIE = 2;
    private final int MAXIMUM_NUMBER_OF_DIE = 6;
    private final int MAXIMUM_NUMBER_OF_ROWS = 2;
    private final int MAXIMUM_NUMBER_OF_COLLUMNS = 3;
    private final String TAG = "DieActivity";
    //----------------------------------------------

    //---------------Views--------------------------
    private Button btnHistory;
    private Button btnRoll;
    private LinearLayout pnlDie;
    private Spinner spnNumbers;
    //----------------------------------------------

    //----------------variables---------------------
    private int dieAmount;
    private ArrayList<ImageView> dieImages;
    private IDieCup dieCup;
    //----------------------------------------------



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_die);

        //-------------These are values, that we have to get from the bundle-----
        dieAmount = 2;
        dieImages = new ArrayList<>(); // when this is recieved from the bundle the images should be added to all the ImageViews.
        dieCup = new DieCup();
        //-----------------------------------------------------------------------
        getWidgets();
        setUpSpnNumbers();
        setUpPnlDie();
        setDieImages();
        setUpButtons();
    }

    /**
     * This will set the correct image of the dice.
     */
    private void setDieImages() {
        int dieImagePosition = 0;
        for(Dice die : (Dice[])dieCup.getAll()){
            int resourceId = R.drawable.die4;
            int faceValue = die.getFace();

            switch (faceValue){
                case 1 :
                    Log.d(TAG,"die1 has been set as the resource");
                    resourceId = R.drawable.die1;
                    break;
                case 2 :
                    resourceId = R.drawable.die2;
                    break;
                case 3:
                    resourceId = R.drawable.die4;
                    break;
                case 4:
                    resourceId =R.drawable.die4;
                    break;
                case 5:
                    resourceId = R.drawable.die5;
                    break;
                case 6:
                    resourceId = R.drawable.die6;
                    break;
            }
            ImageView image = dieImages.get(dieImagePosition);
            Log.d(TAG, "we get hold of the image view" + image);
            image.setImageResource(resourceId);
            dieImagePosition++;
        }
    }

    /**
     * This method will set up the pnlDie panel.
     * in order to access all the imageviews you can use the
     */
    private void setUpPnlDie() {
        //clears the views already in the linear layout
        pnlDie.removeAllViews();

        Log.d(TAG, "The views removed");
        int totalImages = 0;
        for (int row = 0; row < MAXIMUM_NUMBER_OF_ROWS && totalImages < dieAmount; row++) {
            LinearLayout dieRow = addRowLayout(pnlDie);
            Log.d(TAG, "row added");
            for (int column = 0; column < MAXIMUM_NUMBER_OF_COLLUMNS && totalImages < dieAmount; column++) {
                addImageToLayout(dieRow);
                totalImages++;
                Log.d(TAG, "image view added");
            }
        }
    }

    /**
     * this will add an imageView to the given layout, it will then save a refference to the views,
     * so they can be changed later
     * @param layout the layout, to add an imageview to.
     */
    private void addImageToLayout(LinearLayout layout) {
        ImageView view = new ImageView(this);
        layout.addView(view);
        dieImages.add(view);
    }

    /**
     * Adds a row to the given item, a row is a linear layout, with a horizontal layout
     * @param item the layout to add to.
     * @return the row that was created
     */
    private LinearLayout addRowLayout(LinearLayout item) {
        LinearLayout dieRow = new LinearLayout(this);
        dieRow.setOrientation(LinearLayout.HORIZONTAL);
        item.addView(dieRow);
        return dieRow;
    }

    /**
     * sets up the spnNumbers
     */
    private void setUpSpnNumbers() {
        //---------set up numbers----------
        Integer[] numbers = getSpinnerNumbers();
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, numbers);

        spnNumbers.setAdapter(adapter);
        //---------------------------------

        //---------set up listener---------
        spnNumbers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setDieAmount(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //--------------------------------

    }

    /**
     * will set the dieAmount, and rebuild the diePanel
     *
     * @param position the position in the adapter, where the value is.
     */
    private void setDieAmount(int position) {
        dieAmount = (Integer) spnNumbers.getAdapter().getItem(position);
        setUpPnlDie();
    }

    /**
     * creates an array of intergers, with the values that correspond to the amount of die, that can be picked
     * @return an array of integers.
     */
    private Integer[] getSpinnerNumbers() {
        //gets the amount of die allowed in the cup.
        int dieAmount = MAXIMUM_NUMBER_OF_DIE - MINIMUM_NUMBER_OF_DIE;
        Integer[] result = new Integer[dieAmount + 1];

        //puts the values in the correct position
        for (int i = 0; i <= dieAmount; i++) {
            result[i] = i + MINIMUM_NUMBER_OF_DIE;
        }
        return result;
    }

    /**
     * Collects the widgets, and sets them to the instance variables.
     */
    private void getWidgets() {
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
