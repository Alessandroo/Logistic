package com.logistic.controllers.servlets;

import com.logistic.dao.exceptions.DAOException;
import com.logistic.dao.exceptions.DublicateKeyDAOException;
import com.logistic.dao.exceptions.InternalDAOException;
import com.logistic.dao.exceptions.InvalidDataDAOException;
import com.logistic.model.systemunits.entities.Order;
import com.logistic.model.systemunits.entities.Point;
import com.logistic.model.systemunits.entities.Road;
import com.logistic.model.systemunits.entities.User;
import com.logistic.model.systemunits.orm.ORMOrder;
import com.logistic.model.systemunits.orm.ORMUser;
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
        switch (req.getParameter("action")) {
            case "add" : {
                addOrders(req,res);
                break;
            } case "edit" : {
                int temp = Integer.getInteger(req.getParameter("id"));
                editOrders(req,res,temp);
                break;
            } case "delete" : {
                int temp = Integer.getInteger(req.getParameter("id"));
                deleteOrders(req,res,temp);
                break;
            }
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {                //сохранение
        try {
            Order simpleOrder  = new Order();
            simpleOrder.setId(Integer.getInteger(req.getParameter("id")));
            ORMOrder order = new ORMOrder();

            RequestDispatcher editView = req.getRequestDispatcher(ORDER_LIST_URL);
            editView.forward(req, res);
        }catch (Exception e) {
            forwardToErrorPage(req,res);
        }
    }

    public void addOrders(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        RequestDispatcher editView = req.getRequestDispatcher(ORDER_EDIT_PAGE);
        editView.forward(req, res);
    }

    public void editOrders(HttpServletRequest req, HttpServletResponse res, int id)
            throws ServletException, IOException {
        try {
            Order simpleOrder  = new Order();
            simpleOrder.setId(Integer.getInteger(req.getParameter("id")));
            ORMOrder order = new ORMOrder();
            order.setEntity(simpleOrder);
            order.read();
            req.setAttribute("order", order);
            RequestDispatcher editView = req.getRequestDispatcher(ORDER_EDIT_PAGE);
            editView.forward(req, res);
        }catch (Exception e) {
            forwardToErrorPage(req,res);
        }


    }
    public void deleteOrders(HttpServletRequest req, HttpServletResponse res, int id)
            throws ServletException, IOException {
        try {
            Order simpleOrder  = new Order();
            simpleOrder.setId(Integer.getInteger(req.getParameter("id")));
            ORMOrder order = new ORMOrder();
            order.setEntity(simpleOrder);
            order.delete();
            RequestDispatcher editView = req.getRequestDispatcher(ORDER_LIST_URL);
            editView.forward(req, res);
        }catch (Exception e) {
            forwardToErrorPage(req,res);
        }
    }

}