package com.logistic.model.systemunits.entities;

/**
 * Created by Vojts on 10.12.2016.
 */
public class Order extends Entity{
    private User client;
    private DeliveryClass deliveryClass;
    private Cargo cargo;
    private Route route;
    private Road road;
    private TimeTable timeTable;
    private float calculation;

    public float getCalculation() {
        return calculation;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public DeliveryClass getDeliveryClass() {
        return deliveryClass;
    }

    public TimeTable getTimeTable() {
        return timeTable;
    }

    public User getClient() {
        return client;
    }

    public Road getRoad() {
        return road;
    }

    public Route getRoute() {
        return route;
    }

    public void setCalculation(float calculation) {
        this.calculation = calculation;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public void setDeliveryClass(DeliveryClass deliveryClass) {
        this.deliveryClass = deliveryClass;
    }

    public void setTimeTable(TimeTable timeTable) {
        this.timeTable = timeTable;
    }

    public void setRoad(Road road) {
        this.road = road;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
