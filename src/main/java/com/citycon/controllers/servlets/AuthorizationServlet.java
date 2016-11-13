package com.citycon.controllers.servlets;

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
public class AuthorizationServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        ORMUser user = new ORMUser();

        user.setLogin(request.getParameter("login"));
        user.setPassword(request.getParameter("password"));

        RequestDispatcher rd;

        try {
            user.read();
            request.getSession().setAttribute("user",user.getEntity());
            rd = getServletContext().getRequestDispatcher("/app");
            //перенаправление на страницу после аунтефикации(пока не созданы) (заменить /app и /error)
        } catch (ORMException e) {
            e.printStackTrace();
            rd = getServletContext().getRequestDispatcher("/error");
            //TODO: logging
            //TODO: error page
        }


        rd.forward(request,response);

    }
}