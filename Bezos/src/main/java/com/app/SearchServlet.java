package com.app;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// JDBC URL, username, and password of SQLite database

	private static List<Item> items;
	private ItemDAO itemDAO = new ItemDAO();

	public SearchServlet() {
		super();
	}

	public void init() throws ServletException {
		// Load the SQLite JDBC driver class when the servlet is initialized
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String searchText = request.getParameter("search");
		System.out.println(searchText);
		createItemsTable();
		makeList(searchText);

		PrintWriter out = response.getWriter();

		// Generate the HTML for the new row
		out.println("            <tr>\r\n" + "                <th>Item Name</th>\r\n"
				+ "                <th>Current Price</th>\r\n" + "                <th>Auction Type</th>\r\n"
				+ "                <th>Remaining Time</th>\r\n" + "                <th>Description</th>\r\n"
				+ "                <th>Shipping</th>\r\n" + "                <th>Select</th>\r\n"
				+ "            </tr>");
		for (Item item : items) {
			String newRow = "<tr><td>" + item.getName() + "</td><td>" + item.getCost() + "</td><td>" + item.getType()
					+ "</td><td>" + item.getDate().toString() + "</td><td>" + item.getDescription() + "</td><td>"
					+ item.getShipping() + "</td><td><input type=\"radio\" " 
					+ " id=\"select\" name=\"item_select\" value=\"" + item.getId() + "\"></td></tr>";

			out.println(newRow);
			System.out.println(item.getId());
		}

	}

	private void createItemsTable() {
		try (Connection conn = DatabaseConnection.connect(); Statement statement = conn.createStatement()) {
			String createTableSQL = "CREATE TABLE IF NOT EXISTS items ("
					+ "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + "name TEXT," + "price DECIMAL(10,2),"
					+ "type TEXT," + "endtime DATETIME," + "description TEXT,"
					+ "shipping DECIMAL(10,2), sold BIT DEFAULT 0);"; // Added
			// description
			// and shipping
			// columns
			statement.executeUpdate(createTableSQL);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void makeList(String searchText) {
		try {
			Connection conn = DatabaseConnection.connect();
			String sql;
			PreparedStatement preparedStatement;
			if (searchText == null || searchText.trim().isEmpty()) {
				sql = "SELECT * FROM items";
				preparedStatement = conn.prepareStatement(sql);
			} else {
				System.out.println("good");
				sql = "SELECT * FROM items WHERE name LIKE ?";
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setString(1, "%" + searchText + "%");
			}

			createItemsTable();

			ResultSet rows = preparedStatement.executeQuery();

			items = new ArrayList<>();

			while (rows.next()) {
				items.add(new Item(rows.getInt("id"), rows.getString("name"), rows.getDouble("price"),
						rows.getString("type"), rows.getTimestamp("endtime"), rows.getString("description"),
						rows.getDouble("shipping")));
			}

			preparedStatement.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
