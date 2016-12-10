package com.logistic.controllers.servlets;

import com.logistic.controllers.servlets.AbstractHttpServlet;
import com.logistic.model.systemunits.entities.RouterEntity;
import com.logistic.model.systemunits.orm.ORMRouter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by root on 16.11.16.
 */
public class OrderListServlet  extends AbstractHttpServlet {
    private static String RUTERS_LIST_PAGE = "/jsp/orders/orderList.jsp";

    private String ROUTER_IS_EXIST = "Router with that SN is already exist";
    private String SERVER_ERROR = "Server error";
    private String INVALID_DATA = "Invalid Data";



    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        /*RouterEntity routers[] = null;
        RequestDispatcher view = null;
        try {

            routers = ORMRouter.getPage(1,20,"name",true);
            request.setAttribute("entityArray", routers);

            view = request.getRequestDispatcher(RUTERS_LIST_PAGE);
        } catch (Exception e) {
            forwardToErrorPage("error", request, response);
        }*/
        RequestDispatcher view = request.getRequestDispatcher(RUTERS_LIST_PAGE);
        view.forward(request, response);
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