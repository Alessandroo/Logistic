package com.logistic.model.systemunits.entities;

import java.util.ArrayList;

/**
 * Created by Vojts on 10.12.2016.
 */
public class CarCrew extends Entity {
    private ArrayList<User> drivers;
    private Route route;
    private Truck truck;
    private ArrayList<Order> orders;

    public Route getRoute() {
        return route;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public ArrayList<User> getDrivers() {
        return drivers;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void setDrivers(ArrayList<User> drivers) {
        this.drivers = drivers;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }
}
