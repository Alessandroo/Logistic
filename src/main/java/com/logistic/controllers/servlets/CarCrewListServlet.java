package com.logistic.controllers.servlets;

import com.logistic.dao.exceptions.DAOException;
import com.logistic.model.systemunits.entities.Order;
import com.logistic.model.systemunits.entities.User;
import com.logistic.model.systemunits.orm.ORMUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by root on 12.12.16.
 */
public class CarCrewListServlet extends AbstractHttpServlet {
    private static String ORDER_LIST_PAGE = "/jsp/orders/orderList.jsp";

    private String ROUTER_IS_EXIST = "Router with that SN is already exist";
    private String SERVER_ERROR = "Server error";
    private String INVALID_DATA = "Invalid Data";



    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        User user = new User();
        user.setLogin("Alex");
        user.setPassword("Adminas");
        ORMUser client = new ORMUser();
        client.setEntity(user);
        try {
            client.read();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        User myClient = client.getEntity();
        Order OrderList[] = new Order[10];
        Order oo = new Order();
        oo.setClient(myClient);
        oo.setTimeTable(null);
        oo.setCalculation(5);
        oo.setCargo(null);
        oo.setRoad(null);
        for (int i = 0; i< 10; i++) {
            OrderList[i] = oo;
        }
        try {
            request.setAttribute("entityArray", OrderList);
            request.getRequestDispatcher(ORDER_LIST_PAGE).forward(request, response);
        } catch (Exception e) {

        }

    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}