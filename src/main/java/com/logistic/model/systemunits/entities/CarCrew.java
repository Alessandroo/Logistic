package com.logistic.model.systemunits.entities;

import java.util.ArrayList;

/**
 * Created by Vojts on 10.12.2016.
 */
public class CarCrew extends Entity {
    private ArrayList<User> drivers;
    private RouteArray route;
    private Truck truck;

    public RouteArray getRoute() {
        return route;
    }

    public ArrayList<User> getDrivers() {
        return drivers;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setRoute(RouteArray route) {
        this.route = route;
    }

    public void setDrivers(ArrayList<User> drivers) {
        this.drivers = drivers;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }
}
