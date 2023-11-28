package com.app;

import jakarta.servlet.RequestDispatcher;
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

import com.app.Objects.Item;
import com.app.Objects.ItemDAO;

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

		int id = Integer.valueOf(request.getParameter("itemId"));

		
		if (!itemDAO.isAuctionActive(request.getParameter("itemId"))) {
			response.sendRedirect("AuctionEnd.html?itemId=" + id);
			
		}else {
			response.sendRedirect("item.html?itemId=" + id);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String username = session.getAttribute("username").toString();
		String type = request.getParameter("auctionTypeHidden");
		String itemId = request.getParameter("itemId");
		System.out.print(type);
		
		
		if (!itemDAO.isAuctionActive(request.getParameter("itemId"))) {
			response.sendRedirect("AuctionEnd.html?itemId=" + itemId);
			return;
		}
		
		if (type.equals("forward")) {
			double amount = Double.parseDouble(request.getParameter("amount"));
			itemDAO.forwardAuction( itemId, username, amount);
			response.sendRedirect("item.html?itemId=" + itemId);
		} else {
			itemDAO.dutchAuction(itemId, username);
			response.sendRedirect("AuctionEnd.html?itemId=" + itemId);

			
		}

	}


}
