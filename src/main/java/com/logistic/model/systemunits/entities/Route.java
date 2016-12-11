package com.logistic.model.systemunits.entities;

/**
 * Created by Vojts on 10.12.2016.
 */
public class Route extends Entity {
    private Road road;
    private Order order;

    public Order getOrder() {
        return order;
    }

    public Road getRoad() {
        return road;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setRoad(Road road) {
        this.road = road;
    }
}
