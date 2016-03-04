package com.tictactoe.max.dietime.models.implement;

import com.tictactoe.max.dietime.models.abstraction.IDieCup;
import com.tictactoe.max.dietime.models.abstraction.IDieLog;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Max on 29-02-2016.
 */
public class DieLog implements Serializable, IDieLog{
    private static DieLog instance;
    private ArrayList<IDieCup> history;
    /**
     * Singleton
     * @return the single instance that will ever be made.
     */
    public static DieLog getInstance(){
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
    public void add(IDieCup toAdd){
        history.add(toAdd);
    }

    /**
     * Clears the history
     */
    @Override
    public void clear() {
        history.clear();
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
