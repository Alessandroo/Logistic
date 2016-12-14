package com.logistic.controllers.servlets;

import com.logistic.model.systemunits.entities.CarCrew;
import com.logistic.model.systemunits.entities.GraphPoint;
import com.logistic.model.systemunits.entities.Order;
import com.logistic.model.systemunits.entities.Route;
import com.logistic.model.systemunits.orm.ORMCarCrew;
import com.logistic.model.systemunits.orm.ORMOrder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by root on 16.11.16.
 */
public class RecomendationListServlet extends AbstractHttpServlet {
    private static String LIST_PAGE = "/jsp/carCrews/recomendation.jsp";

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        try {
            int id = Integer.valueOf(request.getParameter("id"));
            CarCrew simpleCrew = new CarCrew();
            ORMCarCrew carCrew = new ORMCarCrew();
            simpleCrew.setId(id);
            carCrew.setEntity(simpleCrew);
            try {
                carCrew.read();
            } catch (Exception e) {
                forwardToErrorPage("vot",request,response);
            }
            ArrayList<Route> routes = null;
            Route routesList[] = null;
            routes = carCrew.getEntity().getRoute().getRoutes();
            try {
                routesList = routes.toArray( new Route[routes.size()] );
            } catch (Exception e) {
                forwardToErrorPage("vot vot",request,response);
            }//routesList[0].getRoad().getPointBegin()
            //findMinPath(routesList);
            request.setAttribute("entityArray", routesList);
            try {
                request.getRequestDispatcher(LIST_PAGE).forward(request, response);
            } catch (Exception e) {

            }

        } catch (Exception e) {
            forwardToErrorPage("recomendation servlet error"+e.getMessage(),request,response);
        }

    }

    void findMinPath(Route[] mas) {
        GraphPoint points[] = new GraphPoint[mas.length];
        int n = mas.length;
        for  (int i = 0; i < n; i++) {
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



    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
