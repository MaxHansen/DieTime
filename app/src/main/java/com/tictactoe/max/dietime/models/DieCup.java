package com.tictactoe.max.dietime.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Max on 29-02-2016.
 */
public class DieCup implements Serializable {
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
    public void RollDie(){
        for (Dice dice : die){
            dice.roll();
        }
    }

}
