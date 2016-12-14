package com.logistic.controllers.servlets;

import com.logistic.dao.exceptions.DAOException;
import com.logistic.model.systemunits.entities.CarCrew;
import com.logistic.model.systemunits.entities.Order;
import com.logistic.model.systemunits.entities.User;
import com.logistic.model.systemunits.orm.ORMCarCrew;
import com.logistic.model.systemunits.orm.ORMOrder;
import com.logistic.model.systemunits.orm.ORMUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by root on 12.12.16.
 */
public class CarCrewListServlet extends AbstractHttpServlet {
    private static String ORDER_LIST_PAGE = "/jsp/carCrews/carCrewList.jsp";


    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        try {
            ORMCarCrew card = new ORMCarCrew();
            CarCrew List[] = card.getPage(1,5000);
            //List[0].getRoute().getRoutes().size()
            request.setAttribute("entityArray", List);
            request.getRequestDispatcher(ORDER_LIST_PAGE).forward(request, response);
        } catch (Exception e) {

        }

    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}