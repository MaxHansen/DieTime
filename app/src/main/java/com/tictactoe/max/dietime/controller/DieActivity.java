package com.tictactoe.max.dietime.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class DieActivity extends Activity {
    //-------------------constants---------------
    private final int MINIMUM_NUMBER_OF_DIE = 2;
    private final int MAXIMUM_NUMBER_OF_DIE = 6;
    private final int MAXIMUM_NUMBER_OF_ROWS = 2;
    private final int MAXIMUM_NUMBER_OF_COLLUMNS = 3;
    private final String TAG = "DieActivity";
    private final String DIE_CUP = "dieCup";
    private final String DIE_AMOUNT = "dieAmount";
    private final String DIE_IMAGES = "dieImages";
    //----------------------------------------------

    //---------------Views--------------------------
    private Button btnHistory;
    private Button btnRoll;
    private LinearLayout pnlDie;
    private Spinner spnNumbers;
    //----------------------------------------------

    //----------------variables---------------------
    private int dieAmount;
    private ArrayList<Integer> dieImages;
    private IDieCup dieCup;
    //----------------------------------------------



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_die);

        getSavedInstances(savedInstanceState);

        getWidgets();
        setUpPnlDie();
        //setDieImages();
        setUpSpnNumbers();

        
        setUpButtons();
    }

    /**
     * this gets the saves instances from the bundle, unless it's the first run.
     * @param savedInstanceState the saved instancestate.
     */
    private void getSavedInstances(Bundle savedInstanceState) {
        if(savedInstanceState == null) {
            dieAmount = 2;
            dieImages = new ArrayList<>(); // when this is recieved from the bundle the images should be added to all the ImageViews.
            dieCup = new DieCup();
        }else{
            dieAmount = savedInstanceState.getInt(DIE_AMOUNT);
            dieImages = (ArrayList<Integer>) savedInstanceState.getSerializable(DIE_IMAGES);
            dieCup = (IDieCup) savedInstanceState.getSerializable(DIE_CUP);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(DIE_AMOUNT, dieAmount);
        outState.putSerializable(DIE_CUP,dieCup);
        outState.putSerializable(DIE_IMAGES,dieImages);
    }

    /**
     * This will set the correct image of the dice.
     */
    private void setDieImages() {
        int dieNumber = 0;
        Dice[] die = (Dice[]) dieCup.getAll();
        for(int id : dieImages){
            ImageView img = (ImageView) findViewById(id);
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


    /**
     * This method will set up the pnlDie panel.
     * in order to access all the imageviews you can use the
     */
    private void setUpPnlDie() {
        //clears the views already in the linear layout
        pnlDie.removeAllViews();
        dieImages.clear();
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
        int imageId = View.generateViewId();
        dieImages.add(imageId);
        view.setId(imageId);
        layout.addView(view);
        view.setImageResource(R.drawable.die2);
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
        Log.d(TAG, "History button clicked..");
        startActivity(intent);
        Log.d(TAG, "History activity started..");
    }

    /**
     * will set the dieAmount, and rebuild the diePanel
     *
     * @param position the position in the adapter, where the value is.
     */
    private void setDieAmount(int position) {
        dieAmount = (Integer) spnNumbers.getAdapter().getItem(position);
        dieCup.setDieAmount(dieAmount);
        setUpPnlDie();
        setDieImages();
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

}
