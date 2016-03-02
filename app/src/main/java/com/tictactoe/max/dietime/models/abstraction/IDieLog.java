package com.tictactoe.max.dietime.models.abstraction;

import com.tictactoe.max.dietime.models.implement.DieCup;

import java.util.ArrayList;

/**
 * Created by Max on 01-03-2016.
 */
public interface IDieLog {
    void add(DieCup cup);
    void clear();
    ArrayList getAll();
}
