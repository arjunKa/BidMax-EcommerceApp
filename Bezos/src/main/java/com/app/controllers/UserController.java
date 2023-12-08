package com.app.controllers;

import com.app.Objects.User;
import com.app.Objects.UserDAO;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/users")
public class UserController {

	private UserDAO userDAO = new UserDAO();

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/login")
	public User getUser(User user) {
		return userDAO.login(user.getUsername(), user.getPassword());
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{username}")
	public User getUser(@PathParam("username") String username) {
		return userDAO.login(username);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/signup")
	public User addUser(User user) {
		userDAO.signUp(user);
		return userDAO.login(user.getUsername(), user.getPassword());
	}

}
