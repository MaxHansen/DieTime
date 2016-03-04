package com.tictactoe.max.dietime.models.abstraction;

import java.io.Serializable;

/**
 * Created by Max on 02-03-2016.
 */
public interface IDieCup extends IRoll, Serializable {
    IRoll[] getAll();
    String getDate();
    void setDieAmount(int amount);
}
