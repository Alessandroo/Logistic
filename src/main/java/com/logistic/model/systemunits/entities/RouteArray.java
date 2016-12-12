package com.logistic.model.systemunits.entities;

import java.util.ArrayList;

/**
 * Created by Vojts on 11.12.2016.
 */
public class RouteArray extends Entity {
    public RouteArray(int number){
        setId(number);
    }
    private ArrayList<Route> routes = new ArrayList<>(200);

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }

    public void addRoute(Road road, Order order){
        Route route = new Route();
        route.setId(getId());
        route.setRoad(road);
        route.setOrder(order);
        routes.add(route);
    }
}
