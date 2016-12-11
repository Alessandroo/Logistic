package com.logistic.controllers.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.logistic.dao.exceptions.DAOException;
import com.logistic.dao.exceptions.InvalidDataDAOException;
import com.logistic.model.systemunits.entities.User;
import com.logistic.model.systemunits.orm.ORMUser;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Used to show the list of users. Support pagination. Redirects to the last available
 * page if some pagination data is invlaid and redirects to the error page if DAOException occurs.
 * 	
 * @author Mike
 * @version 0.3
 */
public class UserListServlet extends AbstractHttpServlet {
	private static String USER_LIST_PAGE = "/jsp/users/userList.jsp";
	private static String USER_LIST_URL = "/users";

	public UserListServlet(){
		super();
		logger = LoggerFactory.getLogger("com.logistic.controllers.servlets.UserListServlet");
	}

	protected void doGet(HttpServletRequest req, 
		HttpServletResponse res) throws ServletException, IOException {

		try {
			HashMap<String, String> paginationParameters = ((HashMap<String, HashMap<String, String>>)req
											.getSession().getAttribute("paginationParameters")).get("users");

			//If page must be normalized (negative or too large)
			if (setPaginationVariables(ORMUser.getCount(), paginationParameters, req, res) != null) {
				StringBuilder redirect = new StringBuilder();
					redirect.append(paginationParameters.get("path"));
					redirect.append("?page=");
					redirect.append(paginationParameters.get("page")); // normalized page
					redirect.append("&itemsPerPage=");
					redirect.append(paginationParameters.get("itemsPerPage"));
					redirect.append("&sortBy=");
					redirect.append(paginationParameters.get("sortBy"));
					redirect.append("&asc=");
					redirect.append(paginationParameters.get("asc"));
					logger.debug("Incorrect page, redirect to the "+redirect.toString());
					res.sendRedirect(redirect.toString());
					return;
			}


			int page = Integer.parseInt(paginationParameters.get("page"));
			int itemsPerPage = Integer.parseInt(paginationParameters.get("itemsPerPage"));
			boolean asc = paginationParameters.get("asc").equals("true");
			String sortBy = paginationParameters.get("sortBy");

			logger.trace("getPage of users with args page:{} itemsPerPage:{}, sortBy:{}, asc:{}",
																page, itemsPerPage, sortBy, asc);

			User[] users = ORMUser.getPage(page, itemsPerPage);
			req.setAttribute("entityArray", users);
			req.getRequestDispatcher(USER_LIST_PAGE).forward(req, res);
		} catch (InvalidDataDAOException | NumberFormatException exception) {
			forwardToErrorPage("Invalid search input", req, res);
			logger.debug("Invalid getPage data", exception);
		} catch (DAOException exception) {
			forwardToErrorPage("Internal DAO exception", req, res);
		} catch (ClassCastException exception) {
			logger.warn("Cannot cast", exception);
			forwardToErrorPage("Internal server error", req, res);
		} catch (Exception exception) {
			logger.warn("Unexpected exception", exception);
			forwardToErrorPage("Internal server error", req, res);
		}
	}
	
	protected void doPost(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
