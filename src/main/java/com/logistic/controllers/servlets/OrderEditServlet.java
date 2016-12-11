package com.logistic.controllers.servlets;

import com.logistic.dao.exceptions.DAOException;
import com.logistic.dao.exceptions.DublicateKeyDAOException;
import com.logistic.dao.exceptions.InternalDAOException;
import com.logistic.dao.exceptions.InvalidDataDAOException;
import com.logistic.model.systemunits.entities.*;
import com.logistic.model.systemunits.orm.*;
import com.sun.org.apache.bcel.internal.generic.FLOAD;
import com.sun.org.apache.xpath.internal.operations.Or;
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
            User simpleUser = new User();
            simpleUser.setLogin(req.getParameter("login"));
            ORMUser user = new ORMUser();
            user.setEntity(simpleUser);
            user.read();

            Cargo simpleCargo = new Cargo();
            simpleCargo.setWeight(Float.parseFloat(req.getParameter("weight")));
            ORMCargo cargo = new ORMCargo();
            TypeCargo simpleTypeCargo = new TypeCargo();
            switch (req.getParameter("type")) {
                case "Class1 - 16km/ch": {
                    simpleTypeCargo.setMax_speed(16);
                    simpleTypeCargo.setName("Class1");
                    break;
                }
                case "Class2 - 40km/ch": {
                    simpleTypeCargo.setMax_speed(16);
                    simpleTypeCargo.setName("Class1");
                    break;
                }
                case "Class3 - 64km/ch": {
                    simpleTypeCargo.setMax_speed(16);
                    simpleTypeCargo.setName("Class1");
                    break;
                }
                case "Class4 - 97km/ch": {
                    simpleTypeCargo.setMax_speed(16);
                    simpleTypeCargo.setName("Class1");
                    break;
                }
                case "Class5 - 127km/ch": {
                    simpleTypeCargo.setMax_speed(16);
                    simpleTypeCargo.setName("Class1");
                    break;
                }
            }
            simpleCargo.setTypeCargo(simpleTypeCargo);
            cargo.setEntity(simpleCargo);

            DeliveryClass simpleDeliveryClass = new DeliveryClass();
            simpleDeliveryClass.setName(req.getParameter("delivery-class"));

            Road simpleRoad = new Road();
            Point simplePointA = new Point();
            String temp =req.getParameter("point-a");
            String y,x;
            temp.getChars(0,6,y,0);
            temp.getChars(8,14,x,0);
            simplePointA.setY(Float.valueOf(y));
            simplePointA.setX(Float.valueOf(x));
            simpleRoad.setPointBegin(simplePointA);

            Point simplePointB = new Point();
            temp =req.getParameter("point-b");
            temp.getChars(0,6,y,0);
            temp.getChars(8,14,x,0);
            simplePointA.setY(Float.valueOf(y));
            simplePointA.setX(Float.valueOf(x));
            simpleRoad.setPointBegin(simplePointA);



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