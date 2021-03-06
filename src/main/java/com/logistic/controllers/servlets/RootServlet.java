package com.logistic.controllers.servlets;

import com.logistic.model.systemunits.entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import org.slf4j.LoggerFactory;

/**
 * Listens the root path of application. Can forward to the index.jsp if user has 
 * not singed in and to the user working page if user has already singed in.
 *
 * @author Mike
 * @version  1.1
 */
public class RootServlet extends AbstractHttpServlet {

    private static final String INDEX_PAGE = "/index.jsp";
    private static final String ADMIN_HOME = "/users";
    private static final String NOT_ADMIN_HOME = "/orders";

    public RootServlet() {
        super();
        logger = LoggerFactory.getLogger("com.logistic.controllers.servlets.RootServlet");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) 
                                        throws ServletException, IOException {
        logger.trace("Root access");
        HttpSession session = req.getSession(false);

        if(session == null || session.getAttribute("user") == null) {
            req.getRequestDispatcher(INDEX_PAGE).forward(req, res);
        } else {
            try {
                User user = (User)session.getAttribute("user");
                if (user.getGroup().equals("admin")) {
                    res.sendRedirect(ADMIN_HOME);
                } else {
                    res.sendRedirect(NOT_ADMIN_HOME);
                }
            } catch (NullPointerException | ClassCastException e) {

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