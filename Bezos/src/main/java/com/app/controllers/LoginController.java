package com.app.controllers;

import com.app.*;
import com.app.Objects.User;
import com.app.Objects.UserDAO;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

@Path("/users")
public class LoginController {

	private UserDAO userDAO = new UserDAO();

	@Context
	private ServletContext servletContext;

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext ctx = sce.getServletContext();

		// ctx.setAttribute("apiCallCounter", 0);
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/login")
	public User getUser(User user) {
		return userDAO.login(user.getUsername(), user.getPassword());
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/signup")
	public User addUser(User user) {
		return userDAO.login(user.getUsername(), user.getPassword());
	}

}
