package com.logistic.model.systemunits.entities;

/**
 * Created by Vojts on 10.12.2016.
 */
public class TypeCargo extends Entity {
    private String name;
    private int max_speed;

    public void setName(String name) {
        this.name = name;
    }

    public void setMax_speed(int max_speed) {
        this.max_speed = max_speed;
    }

    public String getName() {
        return name;
    }

    public int getMax_speed() {
        return max_speed;
    }
}
