package com.logistic.model.systemunits.entities;

/**
 * Created by Vojts on 10.12.2016.
 */
public class Road extends Entity {
    private float longest;
    private Point pointBegin;
    private Point pointEnd;

    public void setLongest(float longest) {
        this.longest = longest;
    }

    public void setPointBegin(Point pointBegin) {
        this.pointBegin = pointBegin;
    }

    public void setPointEnd(Point pointEnd) {
        this.pointEnd = pointEnd;
    }

    public float getLongest() {
        return longest;
    }

    public Point getPointBegin() {
        return pointBegin;
    }

    public Point getPointEnd() {
        return pointEnd;
    }
}
