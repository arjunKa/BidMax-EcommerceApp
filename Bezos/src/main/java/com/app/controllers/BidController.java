package com.app.controllers;

import com.app.Objects.Item;
import com.app.Objects.ItemDAO;
import com.app.Objects.User;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/bid")
public class BidController {

	private ItemDAO itemDAO = new ItemDAO();

	@PUT
	@Path("/purchase/{id}/{total}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Item purchase(@PathParam("id") int id, @PathParam("total") String total, User user) {
		itemDAO.purchase(id, user.getUsername(), total);
		return itemDAO.read(id);
	}

	@PUT
	@Path("/{id}/{total}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Item bid(@PathParam("id") int id, @PathParam("total") String total, User user) {
		Item i = itemDAO.read(id);
		String username = user.getUsername();
		if (i.getType().equals("forward")) {

			itemDAO.forwardAuction(Integer.toString(id), username, Double.parseDouble(total));

		} else if (i.getType().equals("dutch")) {
			itemDAO.dutchAuction(Integer.toString(id), username);

		} else if (i.getType().equals("japanese")) {
			itemDAO.japaneseAuction(Integer.toString(id), username, Double.toString(i.getCost()));

		}
		return itemDAO.read(id);
	}

}
