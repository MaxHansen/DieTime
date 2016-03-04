package com.tictactoe.max.dietime.models.abstraction;

/**
 * Created by Max on 02-03-2016.
 */
public interface IDieCup extends IRoll {
    IRoll[] getAll();
    void setDieAmount(int amount);
}
