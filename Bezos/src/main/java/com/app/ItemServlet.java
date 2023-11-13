package com.app;

import jakarta.servlet.RequestDispatcher;
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
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// JDBC URL, username, and password of SQLite database

	private static Item item;
	private ItemDAO itemDAO = new ItemDAO();

	public ItemServlet() {
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
		try {

			int id = Integer.valueOf(request.getParameter("Item_id"));
			System.out.println(id);
			Connection conn = DatabaseConnection.connect();
			String sql = "SELECT * FROM items WHERE id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);

			ResultSet rows = preparedStatement.executeQuery();

			item = new Item();

			while (rows.next()) {

				item = new Item(rows.getInt(1), rows.getString(2), rows.getDouble(3), rows.getString(4),
						rows.getTimestamp(5));

			}
			System.out.println(item.getCost());
			preparedStatement.close();
			conn.close();

			request.setAttribute("item", item);

			RequestDispatcher view = request.getRequestDispatcher("/ItemPage.jsp");
			view.forward(request, response);
			System.out.println(view + " " + request.getAttribute("item"));
			return;

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

}
