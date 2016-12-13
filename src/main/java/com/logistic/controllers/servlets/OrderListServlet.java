package com.logistic.controllers.servlets;

import com.logistic.dao.exceptions.DAOException;
import com.logistic.model.systemunits.entities.Order;
import com.logistic.model.systemunits.entities.User;
import com.logistic.model.systemunits.orm.ORMOrder;
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

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        try {
            Order OrderList[] = ORMOrder.getPage(1,5000);
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
