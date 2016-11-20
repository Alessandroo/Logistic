package com.citycon.controllers.servlets;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.model.systemunits.entities.RouterConnectionEntity;
import com.citycon.model.systemunits.entities.RouterEntity;
import com.citycon.model.systemunits.orm.ORMRouter;
import com.citycon.model.systemunits.orm.ORMRouterConnection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by dima
 */
public class ConnectionListServlet extends AbstractHttpServlet {
    private static String RUTERS_LIST_PAGE = "/jsp/connections/connectionList.jsp";

    private String ROUTER_IS_EXIST = "Router with that SN is already exist";
    private String SERVER_ERROR = "Server error";
    private String INVALID_DATA = "Invalid Data";



    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        RouterConnectionEntity connections[] = null;
        RequestDispatcher view = null;
        try {

            connections = ORMRouterConnection.getPage(1,20,"id",true);
            request.setAttribute("entityArray", connections);

            view = request.getRequestDispatcher(RUTERS_LIST_PAGE);
        } catch (InvalidDataDAOException e) {
            forwardToErrorPage(INVALID_DATA,request,response);
            e.printStackTrace();
        } catch (InternalDAOException e) {
            forwardToErrorPage(SERVER_ERROR,request,response);
        } catch (DAOException e) {
            forwardToErrorPage(e.getMessage(),request,response);
            e.printStackTrace();
        }

        view.forward(request, response);

    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}