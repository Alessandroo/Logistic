package com.logistic.controllers.filters;

import com.logistic.model.Grant;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;

import java.io.IOException;

/**
 * Checks if user has enough rights to watch such system
 * entities as cities, routers and connections
 * 
 * @author Tim, Mike
 * @version 1.1
 */
public class SystemListFilter extends AbstractHttpFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws ServletException, IOException {

         if (checkRights(req, Grant.NONE, Grant.READ)) {
            chain.doFilter(req, res);
        } else {
            forwardToSecurityErrorPage(req,res);
            return;
        }
    }
}