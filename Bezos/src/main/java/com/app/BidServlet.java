package com.app;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
public class BidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// JDBC URL, username, and password of SQLite database

	private static List<Item> items;

	public BidServlet() {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String type = request.getParameter("auctionTypeHidden").strip();

		if (type.equals("forward")) {
			forwardAuction(request, response, session);
		} else {
			
			PrintWriter out = response.getWriter();
			out.println("<html><body><h2>You bought the item .</h2></body></html>");
			return;

		}

	}

	private void forwardAuction(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		String price = "";
		double amount = 0.0;

		try (Connection conn = DatabaseConnection.connect(); Statement statement = conn.createStatement()) {

			String sql = "INSERT into bids ( username, item_id, bid_amount) VALUES ( ?, ?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			String item_id = request.getParameter("Item_id").strip();
			price = request.getParameter("amount");

			preparedStatement.setString(1, session.getAttribute("username").toString());
			preparedStatement.setInt(2, Integer.parseInt(item_id));

			amount = Double.parseDouble(request.getParameter("amount"));
			preparedStatement.setDouble(3, amount);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.println("<html><body><h2>You bid " + amount + " .</h2></body></html>");
		return;
	}

	private void dutchAuction(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		String price = "";
		double amount = 0.0;

		try (Connection conn = DatabaseConnection.connect(); Statement statement = conn.createStatement()) {

			String sql = "INSERT into bids ( username, item_id, bid_amount) VALUES ( ?, ?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			String item_id = request.getParameter("Item_id").strip();
			price = request.getParameter("amount");

			preparedStatement.setString(1, session.getAttribute("username").toString());
			preparedStatement.setInt(2, Integer.parseInt(item_id));

			amount = Double.parseDouble(request.getParameter("amount"));
			preparedStatement.setDouble(3, amount);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.println("<html><body><h2>You bought for " + amount + " .</h2></body></html>");
		return;
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
					+ item.getShipping() + "</td><td><input type=\"radio\" onclick=\"setSelectedRowData(" + item.getId()
					+ ")\" id=\"select\" name=\"item_select\" value=\"\"></td></tr>";

			out.println(newRow);
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
