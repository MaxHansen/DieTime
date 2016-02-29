package com.tictactoe.max.dietime.models;

import java.io.Serializable;

/**
 * Created by Max on 29-02-2016.
 */
public class DieLog implements Serializable{
    private DieLog instance;
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

    private DieLog() {

    }
}
