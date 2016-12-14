package com.logistic.model.systemunits.entities;

/**
 * Created by Vojts on 10.12.2016.
 */
public class DeliveryClass extends Entity{
    private String name;
    private double price_km;

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice_km(double price_km) {
        this.price_km = price_km;
    }

    public String getName() {
        return name;
    }

    public double getPrice_km() {
        return price_km;
    }
}
