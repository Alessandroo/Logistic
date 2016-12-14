package com.logistic.controllers.servlets;

import com.logistic.model.systemunits.entities.CarCrew;
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
            carCrew.read();
            ArrayList<Route> routes = carCrew.getEntity().getRoute().getRoutes();
            Route routesList[] = null;
            routes.toArray(routesList);
            routesList[0].getOrder().getRoad().getPointBegin().getY();
            request.setAttribute("entityArray", routesList);
            try {
                request.getRequestDispatcher(LIST_PAGE).forward(request, response);
            } catch (Exception e) {

            }

        } catch (Exception e) {
            forwardToErrorPage("recomendation servlet error"+e.getMessage(),request,response);
        }

    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
