package com.citycon.controllers.servlets;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.model.Grant;
import com.citycon.model.systemunits.entities.UserEntity;
import com.citycon.model.systemunits.orm.ORMUser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listens the root path of application. Can forward to the index.jsp if user has 
 * not singed in and to the user working page if user has already singed in.
 *
 * @author Mike
 * @version  1.0
 */
public class RootServlet extends AbstractHttpServlet {

    private static final String INDEX_PAGE = "/jsp/index.jsp";
    private static final String ADMIN_PAGE = "/jsp/users/listUsers.jsp";
    private static final String GUEST_OPERATOR_PAGE = "/jsp/cities/listCities.jsp";

    protected void doGet(HttpServletRequest req, HttpServletResponse res) 
                                        throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("user") == null) {
            RequestDispatcher index = req.getRequestDispatcher(INDEX_PAGE);
            index.forward(req, res);
        } else {
            try {
                UserEntity user = (UserEntity)session.getAttribute("user");
                if (user.getGroup().equals("amdin")) {
                   req.getRequestDispatcher(ADMIN_PAGE).forward(req, res);
                } else {
                    req.getRequestDispatcher(GUEST_OPERATOR_PAGE).forward(req, res);
                }
            } catch (NullPointerException | ClassCastException e) {                
                Logger logger = LoggerFactory.getLogger("com.citycon.controllers.servlets");
                logger.warn("Error during getting user from session ", e);
                forwardToErrorPage("Internal server error", req, res);
            }
            
        }
    }

    protected void doPost(HttpServletRequest req,
                          HttpServletResponse res) throws ServletException, IOException {
       this.doGet(req, res);
    }
}