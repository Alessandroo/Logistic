package com.logistic.controllers.servlets;

import com.logistic.dao.exceptions.DAOException;
import com.logistic.model.systemunits.entities.CarCrew;
import com.logistic.model.systemunits.entities.GraphPoint;
import com.logistic.model.systemunits.entities.Route;
import com.logistic.model.systemunits.orm.ORMCarCrew;

import java.util.ArrayList;

/**
 * Created by root on 14.12.16.
 */
public class val {
    public static void main(String[] arg) {
        int id = 15;
        CarCrew simpleCrew = new CarCrew();
        ORMCarCrew carCrew = new ORMCarCrew();
        simpleCrew.setId(id);
        carCrew.setEntity(simpleCrew);
        try {
            carCrew.read();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        ArrayList<Route> routes = null;
        Route routesList[] = null;
        routes = carCrew.getEntity().getRoute().getRoutes();
        routesList = routes.toArray(new Route[routes.size()]);
        try {
            findMinPath(routesList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static Route[] findMinPath(Route[] mas) throws Exception {
        GraphPoint points[] = new GraphPoint[mas.length];
        int n = mas.length;
        Route ans[] = new Route[mas.length];


        for  (int i = 0; i < n; i++) {
            points[i] = new GraphPoint();
            points[i].x_first = mas[i].getOrder().getRoad().getPointBegin().getX();
            points[i].y_first = mas[i].getOrder().getRoad().getPointBegin().getY();
            points[i].x_last = mas[i].getOrder().getRoad().getPointEnd().getX();
            points[i].y_last = mas[i].getOrder().getRoad().getPointEnd().getY();
            points[i].numberWas = i;
        }

        int temp[] = new int[n];
        int best[] = new int[n];
        for (int i = 0; i < n; i++) {
            temp[i]=i;
            best[i]=i;
        }
        try {
            while (true) {
                System.out.println(temp[0]+" "+temp[1]);
                boolean end = true;
                for (int i = (n-1); i>0; i--) {
                    if (temp[i]>temp[i-1]) {
                        end = false;
                        int replace = i;
                        for (int j= n-1; j >=i; j--) {
                            if (temp[j]>temp[i-1]) {
                                replace = j;
                                break;
                            }
                        }
                        int tt = temp[i-1];
                        temp[i-1]=temp[replace];
                        temp[replace] = tt;
                        for (int j= 0; j < (n-1-i)/2; i++) {
                            int z = temp[j];
                            temp[j]=temp[n-1-j];
                            temp[n-1-j]=z;
                        }
                        break;
                    }
                }
                if(end) {
                    break;
                } else {
                    if (range(points,temp) < range(points,best)) {
                        best = temp;
                    }
                }
            }
        }catch (Exception e) {
            throw new Exception("1");
        }

        try {

            for (int i=0; i< n; i++) {
                ans[best[i]] = mas[i];
            }
        } catch (Exception e) {
            throw new Exception("2");
        }

        return ans;
    }

    static float range(GraphPoint points[], int position[]) {
        float ans = 0;
        for (int i = 0; i< position.length-1; i++) {
            ans+= Math.sqrt(Math.pow(points[position[i]].y_last - points[position[i+1]].y_first,2)
                    + Math.pow(points[position[i]].x_last - points[position[i+1]].x_first,2));
        }
        return ans;
    }
}
