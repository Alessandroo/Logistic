package com.logistic.utils;

import com.logistic.model.systemunits.entities.Point;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

/**
 * Created by Vojts on 14.12.2016.
 */
public class DistanceGeo {
    private Point beginPoint;
    private Point endPoint;
    private double distance;

    public void setBeginPoint(Point beginPoint) {
        this.beginPoint = beginPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public double getDistance() {
        // instantiate the calculator
        GeodeticCalculator geoCalc = new GeodeticCalculator();
        // select a reference elllipsoid
        Ellipsoid reference = Ellipsoid.WGS84;

        GlobalCoordinates point1 = new GlobalCoordinates(beginPoint.getX(), beginPoint.getY());
        GlobalCoordinates point2 = new GlobalCoordinates(endPoint.getX(), endPoint.getY());

        GeodeticCurve geoCurve = geoCalc.calculateGeodeticCurve(
                reference, point1, point2
        );

        return geoCurve.getEllipsoidalDistance() / 1000.0;
    }
}
