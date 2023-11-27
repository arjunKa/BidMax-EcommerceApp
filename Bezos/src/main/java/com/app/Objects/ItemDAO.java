package com.app.Objects;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.app.DatabaseConnection;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ItemDAO {

	private static List<Item> Items = new ArrayList<>();

	public Item create(Item item) {
		Items.add(item);
		return item;
	}

	public List<Item> readAll() {
		return Items;
	}

	public Item create(Item item, String username) {
		try (Connection conn = DatabaseConnection.connect()) {
			String itemName = item.getName();
			String price = String.valueOf(item.getCost());
			String type = item.getType();
			double shipping = item.getShipping();
			String desc = item.getDescription();
			double price_val = Double.valueOf(price);

			String sql = "INSERT INTO items (name, seller_username, price, type, description, shipping, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
			try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

				preparedStatement.setString(1, itemName);
				
				preparedStatement.setString(2, username);
				preparedStatement.setDouble(3, price_val);
				preparedStatement.setString(4, type);
				preparedStatement.setString(5, desc);
				preparedStatement.setDouble(6, shipping);

				LocalDateTime currentDateTime = LocalDateTime.now();

				// Define the desired date-time format
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

				// Format the current date and time
				String formattedDateTime = currentDateTime.format(formatter);

				preparedStatement.setString(7, formattedDateTime);

				preparedStatement.executeUpdate();
				preparedStatement.close();
				conn.close();
			}

			//response.sendRedirect("Search.html");

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return item;
	}

	public Item read(int id) {
		Item i = new Item();
		try {
			Connection conn = DatabaseConnection.connect();
			String sql;
			PreparedStatement preparedStatement;

			sql = "SELECT * FROM items WHERE id =" + id;
			preparedStatement = conn.prepareStatement(sql);

			ResultSet rows = preparedStatement.executeQuery();

			while (rows.next()) {
				i = (new Item(rows.getInt("id"), rows.getString("name"), rows.getString("seller_username"),
						rows.getString("bidder_username"), rows.getDouble("price"), rows.getString("type"),
						rows.getString("created_at"), rows.getString("description"), rows.getDouble("shipping")));
			}

			preparedStatement.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return i;
	}

//	public Item update(int id, Item Item) {
//		// Implement update logic
//		// ...
//		
//		return updatedItem;
//	}

	public void delete(int id) {
		Items.removeIf(s -> s.getId() == id);
	}

	public void forwardAuction(String item_id, String username, double amount) {

		
		try (Connection conn = DatabaseConnection.connect(); Statement statement = conn.createStatement()) {

			System.out.println("ID" + item_id);

			if (!isGreater(item_id, amount, conn)) {
				System.out.println("nee");
				return;
			}
			System.out.println("nee");
			String sql = "UPDATE items SET bidder_username = ?, price = ? WHERE id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setString(1, username);
			preparedStatement.setDouble(2, amount);
			preparedStatement.setString(3, item_id);

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			conn.close();
			//response.sendRedirect("Search.html");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return;

	}

	private boolean isGreater(String item_id, double bid_amount, Connection conn) {
		try (Statement statement = conn.createStatement()) {

			String sql = "SELECT price FROM items WHERE id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, item_id);

			ResultSet rows = preparedStatement.executeQuery();

			if (rows.next()) {
				double maxBid = rows.getDouble("price");
				if (bid_amount > maxBid) {

					return true;
				}

				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

	public void dutchAuction(String item_id, String username) {


		try (Connection conn = DatabaseConnection.connect(); Statement statement = conn.createStatement()) {

			String sql = "UPDATE items SET bidder_username = ? WHERE id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setString(1, username);
			preparedStatement.setInt(2, Integer.parseInt(item_id));

			preparedStatement.executeUpdate();
			setToSold(item_id, conn);
			preparedStatement.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		//PrintWriter out = response.getWriter();
		//out.println("<html><body><h2>You bought for " + amount + " .</h2></body></html>");

		//response.sendRedirect("AuctionEnded.jsp?itemId=" + item_id);
		// RequestDispatcher dispatcher =
		// request.getRequestDispatcher("AuctionEndedServlet");
		// dispatcher.forward(request, response);

		return;
	}

	public boolean isAuctionActive(String item_id) {

		try (Connection conn = DatabaseConnection.connect(); Statement statement = conn.createStatement()) {

			String sql = "SELECT * FROM items WHERE id = ? AND sold = 0";

			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, item_id);

			ResultSet rows = preparedStatement.executeQuery();

			if (rows.next()) {

				String createdAt = rows.getString("created_at");

				Item item = new Item(createdAt);

				if (item.getRemainingTime() <= 0) {
					setToSold(item_id, conn);
					System.out.println(item_id + "good");
					return false;
				}

				return true;
			} else {
				System.out.println("empty Row");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	private void setToSold(String item_id, Connection conn) {

		try (Statement statement = conn.createStatement()) {

			String sql = "UPDATE items SET sold = 1 WHERE id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, item_id);
			preparedStatement.executeUpdate();
			System.out.println(item_id + "good");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}