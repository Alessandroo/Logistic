package com.logistic.controllers.servlets;

import com.logistic.dao.exceptions.DAOException;
import com.logistic.dao.exceptions.InvalidDataDAOException;
import com.logistic.model.systemunits.entities.RouterConnectionEntity;
import com.logistic.model.systemunits.entities.RouterEntity;
import com.logistic.model.systemunits.entities.CityEntity;
import com.logistic.model.systemunits.orm.ORMRouterConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import org.slf4j.LoggerFactory;

/**
 * Used to show the list of connections. Support pagination. Redirects to the last available
 * page if some pagination data is invlaid and redirects to the error page if DAOException occurs.
 *  
 * @author Mike
 * @version 0.3
 */
public class ConnectionListServlet extends AbstractHttpServlet {
    private static String CONNECTION_LIST_PAGE = "/jsp/connections/connectionList.jsp";
    private static String CONNECTION_LIST_URL = "/connections";

    public ConnectionListServlet(){
        super();
        logger = LoggerFactory.getLogger("com.logistic.controllers.servlets.ConnectionListServlet");
    }

    protected void doGet(HttpServletRequest req, 
        HttpServletResponse res) throws ServletException, IOException {
        
        RouterConnectionEntity[] connections;
        HashMap<String, String> paginationParameters;
        try {
            paginationParameters = ((HashMap<String, HashMap<String, String>>)(req
                                            .getSession().getAttribute("paginationParameters"))).get("connections");
        } catch (ClassCastException e) {
            logger.warn("Cannot cast paginationParameters to HashMap: ", e);
            forwardToErrorPage(req, res);
            return;
        }
        
        
        try {
            // Getting page for concrete router
            if (req.getParameter("SN") != null && !req.getParameter("SN").equals("")) {
                RouterEntity router = new RouterEntity();
                router.setSN(req.getParameter("SN"));
                StringBuilder redirect = setPaginationVariables(ORMRouterConnection.getCount(router), 
                                                                paginationParameters, req, res);
                if (redirect != null) {
                    redirect.append("&SN=");
                    redirect.append(req.getParameter("SN"));
                    logger.trace("Incorrect page, redirect to the "+redirect.toString());
                    res.sendRedirect(redirect.toString());
                    return;
                }
                int page = Integer.parseInt(paginationParameters.get("page"));
                int itemsPerPage = Integer.parseInt(paginationParameters.get("itemsPerPage"));
                boolean asc = paginationParameters.get("asc").equals("true");
                String sortBy = paginationParameters.get("sortBy");

                logger.trace("getPage of connections with args page:{} itemsPerPage:{}, sortBy:{}, asc:{}",
                                                                page, itemsPerPage, sortBy, asc);

                connections = ORMRouterConnection.getPage(page, itemsPerPage, sortBy, asc, router);
                
            // Getting page for concrete city
            } else if (req.getParameter("country") != null && req.getParameter("city") != null 
                     && !req.getParameter("country").equals("")  && !req.getParameter("city").equals("")) {
                
                CityEntity city = new CityEntity();
                city.setCountryName(req.getParameter("country"));
                city.setName(req.getParameter("city"));
                StringBuilder redirect = setPaginationVariables(ORMRouterConnection.getCount(city), 
                                                                            paginationParameters, req, res);
                if (redirect != null) {
                    redirect.append("&country=");
                    redirect.append(req.getParameter("country"));
                    redirect.append("&city=");
                    redirect.append(req.getParameter("city"));
                    logger.trace("Incorrect page, redirect to the "+redirect.toString());
                    res.sendRedirect(redirect.toString());
                    return;
                }
                int page = Integer.parseInt(paginationParameters.get("page"));
                int itemsPerPage = Integer.parseInt(paginationParameters.get("itemsPerPage"));
                boolean asc = paginationParameters.get("asc").equals("true");
                String sortBy = paginationParameters.get("sortBy");

                logger.trace("getPage of connections with args page:{} itemsPerPage:{}, sortBy:{}, asc:{}",
                                                                page, itemsPerPage, sortBy, asc);

                connections = ORMRouterConnection.getPage(page, itemsPerPage, sortBy, asc, city);

            // Getting all connections
            } else {
                StringBuilder redirect = setPaginationVariables(ORMRouterConnection.getCount(), 
                                                                    paginationParameters, req, res);
                if (redirect != null) {
                    logger.trace("Incorrect page, redirect to the "+redirect.toString());
                    res.sendRedirect(redirect.toString());
                    return;
                }
                int page = Integer.parseInt(paginationParameters.get("page"));
                int itemsPerPage = Integer.parseInt(paginationParameters.get("itemsPerPage"));
                boolean asc = paginationParameters.get("asc").equals("true");
                String sortBy = paginationParameters.get("sortBy");

                logger.trace("getPage of connections with args page:{} itemsPerPage:{}, sortBy:{}, asc:{}",
                                                                page, itemsPerPage, sortBy, asc);

                connections = ORMRouterConnection.getPage(page, itemsPerPage, sortBy, asc);
            }   

            req.setAttribute("entityArray", connections);
            req.getRequestDispatcher(CONNECTION_LIST_PAGE).forward(req, res);
        } catch (InvalidDataDAOException | NumberFormatException exception) {
            forwardToErrorPage("Invalid search input", req, res);
            logger.debug("Invalid getPage data", exception);
        } catch (DAOException exception) {
            forwardToErrorPage("Internal DAO exception", req, res);
        } catch (Exception exception) {
            logger.warn("Exception", exception);
            forwardToErrorPage("Internal server error", req, res);
        }
    }
    
    protected void doPost(HttpServletRequest req,
            HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
