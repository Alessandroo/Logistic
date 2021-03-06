package com.logistic.controllers.filters;

import com.logistic.model.Grant;
import com.logistic.model.systemunits.entities.User;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.Filter;
import javax.servlet.FilterChain;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Checks if user has enough rights to edit another CityCon users.
 * 
 * @author Tim, Mike
 * @version 1.1
 */
public class UserEditFilter extends AbstractHttpFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        if (checkRights(req, Grant.EDIT, Grant.NONE)) {
            chain.doFilter(req, res);
            return;
        } else {
        	//Allow user to modifu himself even if he is not admin
        	try {
        		HttpServletRequest httpReq = (HttpServletRequest) req;
            	HttpSession session = httpReq.getSession(false);
            	if(session != null && session.getAttribute("user") != null) {
		        	User user = (User)session.getAttribute("user");
		        	if (user.getLogin().equals(req.getParameter("login"))) {
		        		chain.doFilter(req, res);
		        		return;
		        	} 
		        }
        	} catch (Exception e) {
        		Logger logger = LoggerFactory.getLogger("com.logistic.controllers.filters.AbstractHttpFilter");
        		logger.warn("Unexpected exception ", e);
        	}
        	
            forwardToSecurityErrorPage(req,res);
            return;
        }
    }
}