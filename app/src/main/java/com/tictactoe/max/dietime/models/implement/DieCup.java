package com.tictactoe.max.dietime.models.implement;

import com.tictactoe.max.dietime.models.abstraction.IDieCup;
import com.tictactoe.max.dietime.models.abstraction.IRoll;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Max on 29-02-2016.
 */
public class DieCup implements IDieCup {
    private String date;
    private Dice[] die;
    private final int INITIAL_DIE_AMOUNT = 2;

    /**
     * Constructor: creates the die cup.
     */
    public DieCup() {
        setDieAmount(INITIAL_DIE_AMOUNT);
    }

    /**
     * sets the amount of die in the Dice[] array.
     * @param amount the amount of die in the DieCup
     */
    public void setDieAmount(int amount){
        die = new Dice[amount];
        for( int i= 0; i < amount; i++){
            die[i] = new Dice();
        }
    }

    /**
     * Rolls all the die in the cup.
     */
    public void roll(){
        for (Dice dice : die){
            dice.roll();
        }
    }

    /**
     * Gets the date/time
     * @return a string containing the date/time
     */
    public String getDate(){
        return date;
    }

    /**
     * the is used to set the date
     * @param date
     */
    @Override
    public void setDate(String date) {
        this.date=date;
    }

    /**
     * returns all the die.
     * @return an array of die.
     */
    @Override
    public IRoll[] getAll() {
        return die;
    }
}
