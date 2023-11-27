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
//
//	private void forwardAuction(HttpServletRequest request, HttpServletResponse response, HttpSession session)
//			throws ServletException, IOException {
//
//		double amount = 0.0;
//
//		try (Connection conn = DatabaseConnection.connect(); Statement statement = conn.createStatement()) {
//
//			String item_id = request.getParameter("itemId").strip();
//			System.out.println("ID" + item_id);
//			if (!isAuctionActive(item_id, conn)) {
//
//				response.sendRedirect("AuctionEnd.html?itemId=" + item_id);
//				// RequestDispatcher dispatcher =
//				// request.getRequestDispatcher("AuctionEndedServlet");
//				// dispatcher.forward(request, response);
//			}
//
//			amount = Double.parseDouble(request.getParameter("amount"));
//
//			if (!isGreater(item_id, amount, conn)) {
//
//				return;
//			}
//
//			String sql = "UPDATE items SET bidder_username = ?, price = ? WHERE id = ?";
//			PreparedStatement preparedStatement = conn.prepareStatement(sql);
//
//			preparedStatement.setString(1, session.getAttribute("username").toString());
//			preparedStatement.setDouble(2, amount);
//			preparedStatement.setInt(3, Integer.parseInt(item_id));
//
//			preparedStatement.executeUpdate();
//			preparedStatement.close();
//			conn.close();
//			response.sendRedirect("Search.html");
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return;
//
//	}
//
//	private boolean isGreater(String item_id, double bid_amount, Connection conn) {
//		try (Statement statement = conn.createStatement()) {
//
//			String sql = "SELECT price FROM items WHERE id = ?";
//			PreparedStatement preparedStatement = conn.prepareStatement(sql);
//			preparedStatement.setString(1, item_id);
//
//			ResultSet rows = preparedStatement.executeQuery();
//
//			preparedStatement.close();
//
//			if (rows.next()) {
//				double maxBid = rows.getDouble("price");
//				if (bid_amount > maxBid) {
//					return true;
//				}
//				return false;
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return false;
//
//	}
//
//	private void dutchAuction(HttpServletRequest request, HttpServletResponse response, HttpSession session)
//			throws ServletException, IOException {
//
//		double amount = 0.0;
//		String item_id = request.getParameter("Item_id").strip();
//		try (Connection conn = DatabaseConnection.connect(); Statement statement = conn.createStatement()) {
//
//			if (!isAuctionActive(item_id, conn)) {
//				RequestDispatcher dispatcher = request.getRequestDispatcher("AuctionEndedServlet");
//				dispatcher.forward(request, response);
//			}
//
//			String sql = "UPDATE items SET bidder_username = ? WHERE id = ?";
//			PreparedStatement preparedStatement = conn.prepareStatement(sql);
//
//			preparedStatement.setString(1, session.getAttribute("username").toString());
//			preparedStatement.setInt(2, Integer.parseInt(item_id));
//
//			preparedStatement.executeUpdate();
//			preparedStatement.close();
//			setToSold(item_id, conn);
//			conn.close();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		PrintWriter out = response.getWriter();
//		out.println("<html><body><h2>You bought for " + amount + " .</h2></body></html>");
//
//		response.sendRedirect("AuctionEnded.jsp?itemId=" + item_id);
//		// RequestDispatcher dispatcher =
//		// request.getRequestDispatcher("AuctionEndedServlet");
//		// dispatcher.forward(request, response);
//
//		return;
//	}
//
//	private boolean isAuctionActive(String item_id, Connection conn) {
//
//		try (Statement statement = conn.createStatement()) {
//
//			String sql = "SELECT * FROM items WHERE id = ? AND sold = 0";
//
//			PreparedStatement preparedStatement = conn.prepareStatement(sql);
//			preparedStatement.setString(1, item_id);
//
//			ResultSet rows = preparedStatement.executeQuery();
//
//			if (rows.next()) {
//
//				String createdAt = rows.getString("created_at");
//
//				Item item = new Item(createdAt);
//
//				if (item.getRemainingTime() <= 0) {
//					setToSold(item_id, conn);
//					System.out.println(item_id + "good");
//					return false;
//				}
//
//				return true;
//			} else {
//				System.out.println("empty Row");
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return false;
//	}
//
//	private void setToSold(String item_id, Connection conn) {
//
//		try (Statement statement = conn.createStatement()) {
//
//			String sql = "UPDATE items SET sold = 1 WHERE id = ?";
//			PreparedStatement preparedStatement = conn.prepareStatement(sql);
//			preparedStatement.setString(1, item_id);
//			preparedStatement.executeUpdate();
//			System.out.println(item_id + "good");
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//	}

}
