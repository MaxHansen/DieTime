package com.tictactoe.max.dietime.models.implement;

import com.tictactoe.max.dietime.models.abstraction.IDieLog;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Max on 29-02-2016.
 */
public class DieLog implements IDieLog{
    private DieLog instance;
    private ArrayList<DieCup> history;
    /**
     * Singleton
     * @return the single instance that will ever be made.
     */
    public DieLog getInstance(){
        if(instance == null){
            instance = new DieLog();
        }
        return instance;
    }

    /**
     * Constructor: Creates the history
     */
    private DieLog() {
        history = new ArrayList<>();
    }

    /**
     * Adds a diecup to the history
     * @param toAdd the diecup to add to the history.
     */
    @Override
    public void add(DieCup toAdd){
        history.add(toAdd);
    }

    /**
     * Returns the history
     * @return an array list containing the history
     */
    @Override
    public ArrayList getAll() {
        return history;
    }
}
