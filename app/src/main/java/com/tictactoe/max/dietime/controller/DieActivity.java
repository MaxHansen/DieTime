package com.tictactoe.max.dietime.controller;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.tictactoe.max.dietime.R;
import com.tictactoe.max.dietime.models.abstraction.IDieCup;
import com.tictactoe.max.dietime.models.implement.Dice;
import com.tictactoe.max.dietime.models.implement.DieCup;
import com.tictactoe.max.dietime.models.implement.DieLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        Log.d(TAG, "onCreate - Orientation = " + (isLandscape() ? "Horizontal" : "Vertical"));
        if(isLandscape()){
            setContentView(R.layout.activity_die_horizontal);
        }else{
            setContentView(R.layout.activity_die);
        }


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
     * This should set the layout for an imageview
     * @param img
     */
    private void setImageViewLayout(ImageView img){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT );


        params.weight = 1.0f;
        params.gravity = Gravity.CENTER;
        params.setMargins(5, 10, 5, 0);
        img.setLayoutParams(params);

        if(isLandscape()){
            params.setMargins(5,5,5,0);
            img.setMaxHeight(225);
            img.setMaxWidth(225);
            img.setAdjustViewBounds(true);
        }
    }

    /**
     * This will set the correct image of the dice.
     */
    private void setDieImages() {
        int dieNumber = 0;
        Dice[] die = (Dice[]) dieCup.getAll();
        for(int id : dieImages){
            ImageView img = (ImageView) findViewById(id);
            setImageViewLayout(img);
            switch (die[dieNumber].getFace()){
                case 1:
                    img.setImageResource(R.drawable.one);
                    break;
                case 2:
                    img.setImageResource(R.drawable.two);
                    break;
                case 3:
                    img.setImageResource(R.drawable.three);
                    break;
                case 4:
                    img.setImageResource(R.drawable.four);
                    break;
                case 5:
                    img.setImageResource(R.drawable.five);
                    break;
                case 6:
                    img.setImageResource(R.drawable.six);
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
     * this will add an imageView to the given layout, it will then save a reference to the views,
     * so they can be changed later
     * @param layout the layout, to add an imageview to.
     */
    private void addImageToLayout(LinearLayout layout) {
        ImageView view = new ImageView(this);
        int imageId = View.generateViewId();
        dieImages.add(imageId);
        view.setId(imageId);
        layout.addView(view);
        view.setImageResource(R.drawable.two);
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
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, R.layout.spinner_layout, numbers);

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
     * Sets up the buttons
     */
    private void setUpButtons(){
        btnRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRollClick();
            }
        });
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHistoryClick();
            }
        });
    }

    /**
     * When the button roll is clicked, it will set a date, and roll the dices
     */
    private synchronized void onRollClick(){
    
        dieCup.setDate(new SimpleDateFormat("hh:mm:ss").format(new Date()));
        dieCup.roll();
        setRollSound();
        setDieImages();

        DieLog.getInstance().add(dieCup);
        dieCup = new DieCup();
        dieCup.setDieAmount(dieAmount);
    }

    /**
     * This should open the history activity
     */
    private void onHistoryClick(){
        Intent intent = new Intent();
        intent.setClass(this, HistoryActivity.class);
        Log.d(TAG, "History button clicked..");
        startActivity(intent);
        Log.d(TAG, "History activity started..");
    }

    /**
     * This method will get a sound file and play it
     */
    public void setRollSound(){
    Log.d(TAG, "Initializing sounds..");
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.rollsound);
        Log.d(TAG, "Playing sound");
        mp.start();

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

    /**
     * Used to check if the device is turned
     * @return boolean if the orientation is horizontal or not
     */
    private boolean isLandscape()
    {
        Configuration config = getResources().getConfiguration();
        return config.orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

}
