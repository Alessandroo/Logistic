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
 * Created by root on 16.11.16.
 */
public class OrderListServlet  extends AbstractHttpServlet {
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











































/*
package com.logistic.controllers.servlets;

import com.logistic.dao.exceptions.DAOException;
import com.logistic.model.systemunits.entities.CityEntity;
import com.logistic.model.systemunits.entities.UserEntity;
import com.logistic.model.systemunits.orm.ORMCity;
import com.logistic.model.systemunits.orm.ORMUser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;

// Created by dima.
public class CityListServlet extends AbstractHttpServlet {
    private static final long serialVersionUID = 1L;
    private static String CITY_LIST_PAGE = "/jsp/cities/cityList.jsp";
    private static String ERROR_PAGE = "/jsp/error/error.jsp";


    public void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        //Writer wr = response.getWriter();
        //wr.write("hello");

        CityEntity cities[] = null;
        RequestDispatcher view;
        try {

            //получение данных из БД
            cities = ORMCity.getPage(1,20,"name",true);
            request.setAttribute("entityClass", "cities");
            request.setAttribute("entityArray", cities);

            view = request.getRequestDispatcher(CITY_LIST_PAGE);
        } catch (DAOException e) {
            //TODO: logging
            HttpSession session = request.getSession(true);
            session.setAttribute("error",e.getMessage());
            view = request.getRequestDispatcher(ERROR_PAGE);
            e.printStackTrace();
        }

        view.forward(request, response);

    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
*/