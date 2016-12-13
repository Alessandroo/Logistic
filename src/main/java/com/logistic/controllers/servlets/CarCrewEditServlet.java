package com.logistic.controllers.servlets;

import com.logistic.dao.exceptions.DAOException;
import com.logistic.model.systemunits.entities.*;
import com.logistic.model.systemunits.orm.ORMCarCrew;
import com.logistic.model.systemunits.orm.ORMOrder;
import com.logistic.model.systemunits.orm.ORMUser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by root on 12.12.16.
 */
public class CarCrewEditServlet extends  AbstractHttpServlet {

    private String CARD_CREW_EDIT_PAGE = "/jsp/carCrews/carCrewEdit.jsp";
    private String CARD_CREW_LIST_URL = "/carCrews";
    private String CARD_CREW_EDIT_URL = "/carCrew";

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        switch (req.getParameter("action")) {
            case "add" : {
                add(req,res);
                break;
            } case "edit" : {
                edit(req,res);
                break;
            } case "delete" : {
                delete(req,res);
                break;
            } default: {
                add(req,res);
                break;
            }
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {                //сохранение
        try {
            ORMCarCrew carCrew = new ORMCarCrew();
            CarCrew simpleCarCrew = new CarCrew();
            User simpleUser1 = new User();
            ORMUser user = new ORMUser();

            simpleUser1.setLogin(req.getParameter("driver1"));
            user.setEntity(simpleUser1);
            user.read();
            simpleUser1 = user.getEntity();

            User simpleUser2 = new User();
            simpleUser2.setLogin(req.getParameter("driver2"));
            user.setEntity(simpleUser2);
            user.read();
            simpleUser2 = user.getEntity();

            ArrayList<User> drivers = new ArrayList<User>();
            drivers.add(0,simpleUser1);
            drivers.add(1,simpleUser2);

            simpleCarCrew.setDrivers(drivers);

            Truck simpleTruck = new Truck();
            simpleTruck.setNumber(req.getParameter("truck"));
            simpleCarCrew.setTruck(simpleTruck);
            //forwardToErrorPage(simpleCarCrew.getTruck().getNumber(),req,res);

            if (req.getParameter("id").length() > 0) {
                try {
                    simpleCarCrew.setId(Integer.valueOf(req.getParameter("id")));
                    carCrew.setEntity(simpleCarCrew);
                    carCrew.update();
                } catch (Exception e) {
                    forwardToErrorPage("update error",req,res);
                }
            } else {
                carCrew.setEntity(simpleCarCrew);
                try {
                    carCrew.create();
                } catch (Exception e) {
                    forwardToErrorPage("create error",req,res);
                }

            }

        }catch (Exception e) {
            forwardToErrorPage(e.getMessage(),req,res);
        }
    }

    public void add(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        RequestDispatcher editView = req.getRequestDispatcher(CARD_CREW_EDIT_PAGE);
        editView.forward(req, res);
    }

    public void edit(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            CarCrew simpleCarCrew = new CarCrew();
            String temp = req.getParameter("id");
            int id = 1;
            try {
                id = Integer.valueOf(temp);
            } catch (Exception e) {
                forwardToErrorPage("Incorrect URL",req,res);
            }

            try {
                simpleCarCrew.setId(id);
            } catch (Exception e) {
                forwardToErrorPage("Incorrect URL",req,res);
            }


            ORMCarCrew carCrew = new ORMCarCrew();
            carCrew.setEntity(simpleCarCrew);
            carCrew.read();
            try {
                req.setAttribute("carCrew", carCrew.getEntity());
                RequestDispatcher editView = req.getRequestDispatcher(CARD_CREW_EDIT_PAGE);
                editView.forward(req, res);
            } catch (Exception e) {
                forwardToErrorPage("yy"+e.getMessage(),req,res);
            }

        }catch (Exception e) {
            forwardToErrorPage(req,res);
        }


    }
    public void delete(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            CarCrew simpleCarCrew = new CarCrew();
            simpleCarCrew.setId(Integer.valueOf(req.getParameter("id")));
            ORMCarCrew carCrew = new ORMCarCrew();
            carCrew.setEntity(simpleCarCrew);
            carCrew.setEntity(simpleCarCrew);
            try {
                carCrew.delete();
            }catch (Exception e) {
                forwardToErrorPage(e.getMessage(),req,res);
            }



            RequestDispatcher editView = req.getRequestDispatcher(CARD_CREW_LIST_URL);
            editView.forward(req, res);
        }catch (Exception e) {
            forwardToErrorPage(e.getMessage(),req,res);
        }
    }

}