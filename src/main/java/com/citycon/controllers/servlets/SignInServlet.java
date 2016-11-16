package com.citycon.controllers.servlets;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.model.Grant;
import com.citycon.model.systemunits.entities.UserEntity;
import com.citycon.model.systemunits.orm.ORMException;
import com.citycon.model.systemunits.orm.ORMUser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by root on 13.11.16.
 */
public class SignInServlet extends AbstractHttpServlet {
    private static String ERROR = "/error.jsp";

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        ORMUser user = null;
        RequestDispatcher rd;

        try {
            user = new ORMUser();
        } catch (ORMException e) {
            request.getSession().setAttribute("error",e.getMessage());
            rd = getServletContext().getRequestDispatcher(ERROR);
            e.printStackTrace();
        }   //TODO: logging
            //TODO: error page

        user.setLogin(request.getParameter("login"));
        user.setPassword(request.getParameter("password"));

        try {
            user.read();
            request.getSession().setAttribute("user",user.getEntity());
            rd = getServletContext().getRequestDispatcher("/app/users");
            //перенаправление на страницу после аунтефикации(пока не созданы) (заменить /app и /error)
        } catch (ORMException e) {
            e.printStackTrace();
            request.getSession().setAttribute("error",e.getMessage());
            rd = getServletContext().getRequestDispatcher(ERROR);
            //TODO: logging
            //TODO: error page
            //ываыва
        }


        rd.forward(request,response);

    }
}