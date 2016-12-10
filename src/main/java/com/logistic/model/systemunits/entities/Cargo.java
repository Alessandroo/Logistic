package com.logistic.model.systemunits.entities;

/**
 * Created by Vojts on 10.12.2016.
 */
public class Cargo extends Entity {
    private float weight;
    private TypeCargo typeCargo;

    public float getWeight() {
        return weight;
    }

    public TypeCargo getTypeCargo() {
        return typeCargo;
    }

    public void setTypeCargo(TypeCargo typeCargo) {
        this.typeCargo = typeCargo;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
