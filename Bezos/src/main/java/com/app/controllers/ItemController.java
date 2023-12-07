package com.app.controllers;

import java.util.ArrayList;
import java.util.List;

import com.app.*;
import com.app.Objects.Item;
import com.app.Objects.ItemDAO;
import com.app.Objects.User;
import com.app.Objects.UserDAO;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

@Path("/item")
public class ItemController {

	private ItemDAO itemDAO = new ItemDAO();

	@Context
	private ServletContext servletContext;

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext ctx = sce.getServletContext();

		// ctx.setAttribute("apiCallCounter", 0);
	}

	@GET
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Item getItem(@PathParam("id") int id) {
		return itemDAO.read(id);
	}

	@POST
	@Path("/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Item addItem(@PathParam("username") String username, Item item) {
		return itemDAO.create(item, username);
	}
	
	@GET
	@Path("/getUserItems/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Item> getUserItems(@PathParam("username") String username) {
		//List<Item> items = new ArrayList<>();
		return itemDAO.readAll(username);
	}
	
	@GET
	@Path("/getAllItems/{keyword}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Item> getAllItems(@PathParam("keyword") String keyword) {
		//List<Item> items = new ArrayList<>();
		return itemDAO.readAllSearch(keyword);
	}
	
	@GET
	@Path("/getAllItems")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Item> getSearchItems() {
		//List<Item> items = new ArrayList<>();
		return itemDAO.readAllSearch("");
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Item updateItem(@PathParam("id") int id, Item item) {
		itemDAO.update(item, id);
		return itemDAO.read(id);
	}

}
