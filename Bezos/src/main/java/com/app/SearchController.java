package com.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/search")
public class SearchController {
	
	private static List<Item> items;
	private ItemDAO itemDAO = new ItemDAO();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Item createItem(Item item) {
		addItem(item);
		return itemDAO.create(item);
		
	}
	
	private void addItem(Item item) {
		try {
			Connection conn = DatabaseConnection.connect();
			String sql = "INSERT INTO items (name, price, type, endTime) VALUES (?, ?, ?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, item.getName());
			preparedStatement.setDouble(2, item.getCost());
			preparedStatement.setString(3, item.getType());
			preparedStatement.setTimestamp(4, item.getDate());

			int rowsAffected = preparedStatement.executeUpdate();
			preparedStatement.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}

}
