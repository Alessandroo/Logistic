package com.logistic.controllers.servlets;

import com.logistic.dao.exceptions.DAOException;
import com.logistic.dao.exceptions.DublicateKeyDAOException;
import com.logistic.dao.exceptions.InternalDAOException;
import com.logistic.dao.exceptions.InvalidDataDAOException;
import com.logistic.model.systemunits.entities.Order;
import com.logistic.model.systemunits.entities.User;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Represents REST interface for CRUD operations with users. Assumes,
 * that user has successfully logged into system.
 *
 * @author  Dima
 * @version  0.3
 */
public class OrderEditServlet extends AbstractHttpServlet {

    private String ORDER_EDIT_PAGE = "/jsp/orders/orderEdit.jsp";
    private String ORDER_LIST_URL = "/orders";
    private String ORDER_EDIT_URL = "/order";

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        Order order  = new Order();
        User user = new User();
        user.setLogin("Yeeaaa");
        order.setClient(user);
        order.setId(6);
        order.setTimeTable(null);
        order.setCalculation(5);
        order.setCargo(null);
        order.setRoad(null);

        //req.setAttribute("order", order);
        RequestDispatcher editView = req.getRequestDispatcher(ORDER_EDIT_PAGE);
        editView.forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

    }

}