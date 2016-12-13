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
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


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
                editOrders(req,res);
                break;
            } case "delete" : {
                deleteOrders(req,res);
                break;
            }
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {                //сохранение
        try {
            Order order = new Order();
            User simpleUser = new User();
            ORMUser user = new ORMUser();

            simpleUser.setId(Integer.parseInt(req.getParameter("client")));
            user.setEntity(simpleUser);
            user.read();
            simpleUser = user.getEntity();
            order.setClient(simpleUser);

            Cargo simpleCargo = new Cargo();

            simpleCargo.setWeight(Float.parseFloat(req.getParameter("weight")));
            TypeCargo simpleTypeCargo = new TypeCargo();
            simpleTypeCargo.setName(req.getParameter("type"));

            simpleCargo.setTypeCargo(simpleTypeCargo);
            simpleCargo.setName(req.getParameter("cargo"));

            order.setCargo(simpleCargo);

            DeliveryClass simpleDeliveryClass = new DeliveryClass();

            simpleDeliveryClass.setName(req.getParameter("delivery-class"));
            order.setDeliveryClass(simpleDeliveryClass);

            Road simpleRoad = new Road();
            Point simplePointA = new Point();


            String temp =req.getParameter("point-a");
            String t[] = temp.split(" ");
            simplePointA.setX(Float.valueOf(t[0]));
            simplePointA.setY(Float.valueOf(t[1]));

            simpleRoad.setPointBegin(simplePointA);

            Point simplePointB = new Point();


            temp =req.getParameter("point-b");
            String tt[] = temp.split(" ");
            simplePointB.setX(Float.valueOf(tt[0]));
            simplePointB.setY(Float.valueOf(tt[1]));
            simpleRoad.setPointEnd(simplePointB);

            order.setRoad(simpleRoad);

            ORMOrder ormOrder = new ORMOrder();
            ormOrder.setEntity(order);

            try{
                ormOrder.create();
            }catch (DAOException e){
                forwardToErrorPage(e.getMessage(),req,res);
            }

            RequestDispatcher editView = req.getRequestDispatcher(ORDER_LIST_URL);
            editView.forward(req, res);
        }catch (Exception e) {
            forwardToErrorPage(e.getMessage(),req,res);
        }
    }

    public void addOrders(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        RequestDispatcher editView = req.getRequestDispatcher(ORDER_EDIT_PAGE);
        editView.forward(req, res);
    }

    public void editOrders(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            Order simpleOrder  = new Order();
            String temp = req.getParameter("id");
            int id = 1;
            try {
                id = Integer.valueOf(temp);
            } catch (Exception e) {
                forwardToErrorPage("ddd",req,res);
            }

            try {
                simpleOrder.setId(id);
            } catch (Exception e) {
                forwardToErrorPage("pp",req,res);
            }


            ORMOrder order = new ORMOrder();
            order.setEntity(simpleOrder);
            order.read();
            try {
                req.setAttribute("order", order.getEntity());
                RequestDispatcher editView = req.getRequestDispatcher(ORDER_EDIT_PAGE);
                editView.forward(req, res);
            } catch (Exception e) {
                forwardToErrorPage("yy"+e.getMessage(),req,res);
            }

        }catch (Exception e) {
            forwardToErrorPage(req,res);
        }


    }
    public void deleteOrders(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            Order simpleOrder  = new Order();
            simpleOrder.setId(Integer.valueOf(req.getParameter("id")));
            ORMOrder order = null;
            order = new ORMOrder();
            order.setEntity(simpleOrder);
            try {
                order.delete();
            }catch (Exception e) {
                forwardToErrorPage("ooo",req,res);
            }



            RequestDispatcher editView = req.getRequestDispatcher(ORDER_LIST_URL);
            editView.forward(req, res);
        }catch (Exception e) {
            forwardToErrorPage("DDD"+req.getParameter("id"),req,res);
        }
    }

}