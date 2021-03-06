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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Represents REST interface for CRUD operations with users. Assumes,
 * that user has successfully logged into system.
 *
 * @author  Dima
 * @version  0.3
 */
public class OrderEditServlet extends AbstractHttpServlet {

    private String ORDER_ADD_PAGE = "/jsp/orders/orderAdd.jsp";
    private String ORDER_LIST_URL = "/orders";

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            String action = req.getParameter("action");
            if (action == null){
                RequestDispatcher editView = req.getRequestDispatcher(ORDER_LIST_URL);
                editView.forward(req, res);
            }
            switch (action) {
                case "add": {
                    RequestDispatcher editView = req.getRequestDispatcher(ORDER_ADD_PAGE);
                    editView.forward(req, res);
                    break;
                }
                case "addOrder" : {
                    addOrder(req,res);
                }
                case "setOrder" : {
                    setOrder(req,res);
                }
                default: {
                    RequestDispatcher editView = req.getRequestDispatcher(ORDER_LIST_URL);
                    editView.forward(req, res);
                }
            }
        }catch (Exception e){
            forwardToErrorPage(e.getMessage(), req, res);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String type = req.getParameter("action");
        if (type == null) {
            forwardToErrorPage("type parameter is null", req, res);
        }
        switch (type) {
            case "delete": {
                doDelete(req, res);
                return;
            }//сохранение
            case "add": {
                try {
                    Order order = new Order();
                    User simpleUser = new User();

                    simpleUser.setId(Integer.parseInt(req.getParameter("client")));

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


                    String temp = req.getParameter("point-a");
                    String t[] = temp.split(" ");
                    simplePointA.setX(Float.valueOf(t[0]));
                    simplePointA.setY(Float.valueOf(t[1]));

                    simpleRoad.setPointBegin(simplePointA);

                    Point simplePointB = new Point();


                    temp = req.getParameter("point-b");
                    String tt[] = temp.split(" ");
                    simplePointB.setX(Float.valueOf(tt[0]));
                    simplePointB.setY(Float.valueOf(tt[1]));
                    simpleRoad.setPointEnd(simplePointB);

                    order.setRoad(simpleRoad);

                    ORMOrder ormOrder = new ORMOrder();
                    ormOrder.setEntity(order);

                    try {
                        ormOrder.create();
                    } catch (DAOException e) {
                        forwardToErrorPage(e.getMessage(), req, res);
                    }

                    RequestDispatcher editView = req.getRequestDispatcher(ORDER_LIST_URL);
                    editView.forward(req, res);
                } catch (Exception e) {
                    forwardToErrorPage(e.getMessage(), req, res);
                }
            }default:{
                RequestDispatcher editView = req.getRequestDispatcher(ORDER_LIST_URL);
                editView.forward(req, res);
            }

        }
    }

    public void addOrder(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        session.setAttribute("carCrewId", req.getParameter("carCrewId"));
        ORMCarCrew carCrew = new ORMCarCrew();
        CarCrew temp = new CarCrew();
        temp.setId(Integer.valueOf(req.getParameter("carCrewId")));
        carCrew.setEntity(temp);
        try {
            carCrew.read();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        temp = carCrew.getEntity();
        session.setAttribute("carModel", temp.getTruck().getModel());
        RequestDispatcher editView = req.getRequestDispatcher(ORDER_LIST_URL);
        editView.forward(req, res);
    }

    public void setOrder(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        int carCrewId = Integer.valueOf((String)session.getAttribute("carCrewId"));
        ORMCarCrew carCrew = new ORMCarCrew();
        CarCrew simpleCarCrew = new CarCrew();
        simpleCarCrew.setId(carCrewId);
        carCrew.setEntity(simpleCarCrew);
        try {
            carCrew.read();             //считали экипаж
        } catch (DAOException e) {
            e.printStackTrace();
        }

        Order simpleOrder = new Order();
        simpleOrder.setId(Integer.valueOf(req.getParameter("id")));
        ORMOrder order = new ORMOrder();
        order.setEntity(simpleOrder);
        try {
            order.read();               //считали ордер
        } catch (DAOException e) {
            e.printStackTrace();
        }
        simpleOrder = order.getEntity();

        Route simpleRoute = new Route();
        ORMRoute route = new ORMRoute();

        simpleRoute.setOrder(simpleOrder);      //запихали в путь ордер
        simpleRoute.setRoad(simpleOrder.getRoad());


        simpleCarCrew = carCrew.getEntity();
        ArrayList<Route> routes = simpleCarCrew.getRoute().getRoutes();
        routes.add(simpleRoute);
        RouteArray simpleRouteArray = new RouteArray(1);
        simpleRouteArray.setRoutes(routes);  //запихали в массив путей пути


        simpleCarCrew.setRoute(simpleRouteArray);
        carCrew.setEntity(simpleCarCrew);
        try {
            carCrew.update();
        } catch (DAOException e) {
            e.printStackTrace();
        }

        RequestDispatcher editView = req.getRequestDispatcher("/jsp/orders/orderSave.jsp");
        editView.forward(req, res);
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
                forwardToErrorPage(e.getMessage(),req,res);
            }



            RequestDispatcher editView = req.getRequestDispatcher(ORDER_LIST_URL);
            editView.forward(req, res);
        }catch (Exception e) {
            forwardToErrorPage("DDD"+req.getParameter("id"),req,res);
        }
    }

}