package com.logistic.model.systemunits.entities;

import java.util.ArrayList;

/**
 * Created by Vojts on 10.12.2016.
 */
public class Route extends Entity {
    private ArrayList<Road> roadEntities;

    public ArrayList<Road> getRoadEntities() {
        return roadEntities;
    }

    public void setRoadEntities(ArrayList<Road> roadEntities) {
        this.roadEntities = roadEntities;
    }

    public void addRoad(int position, Road road){
        roadEntities.add(position, road);
    }

    public void addRoad(Road road){
        roadEntities.add(road);
    }
}
