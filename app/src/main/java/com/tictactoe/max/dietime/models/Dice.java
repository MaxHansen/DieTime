package com.tictactoe.max.dietime.models;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by Max on 29-02-2016.
 */
public class Dice implements Serializable {
    private int face;
    private Random rand;

    /**
     * Constructor: initialises the randomizer
     */
    public Dice() {
        face = 1;
        rand = new Random(6);
    }

    /**
     * Rolls the dice, setting the face to a random value.
     */
    public void roll(){
        face = 1 + rand.nextInt(6);
    }

    /**
     * Return the value of the face
     * @return the face value of the die
     */
    public int getFace() {
        return face;
    }
}
