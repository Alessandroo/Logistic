package com.logistic.model.systemunits.entities;

/**
 * Created by Vojts on 10.12.2016.
 */
public class Truck extends Entity {
    private String model;
    private String number;
    private Float max_capacity;
    private boolean condition;

    public boolean isCondition() {
        return condition;
    }

    public Float getMax_capacity() {
        return max_capacity;
    }

    public String getModel() {
        return model;
    }

    public String getNumber() {
        return number;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    public void setMax_capacity(Float max_capacity) {
        this.max_capacity = max_capacity;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
