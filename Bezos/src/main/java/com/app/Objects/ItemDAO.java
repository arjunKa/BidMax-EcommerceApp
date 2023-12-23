package com.app.Objects;

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

public class ItemDAO {

	private List<Item> items = new ArrayList<>();

	public Item create(Item item) {
		items.add(item);
		return item;
	}

	public Item update(Item item, int id) {

		try (Connection conn = DatabaseConnection.connect()) {
			String sql = "UPDATE items SET name = ?, description = ?, shipping = ?, price = ?, created_at = ? WHERE id = ?";

			try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

				preparedStatement.setString(1, item.getName());
				preparedStatement.setString(2, item.getDescription());
				preparedStatement.setDouble(3, item.getShipping());
				preparedStatement.setDouble(4, item.getCost());

				LocalDateTime currentDateTime = LocalDateTime.now();

				// Define the desired date-time format
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

				// Format the current date and time
				String formattedDateTime = currentDateTime.format(formatter);

				preparedStatement.setString(5, formattedDateTime);

				preparedStatement.setInt(6, id);

				preparedStatement.executeUpdate();

				preparedStatement.close();
				conn.close();
			}

			// response.sendRedirect("Search.html");

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return item;
	}

	public List<Item> readAll(String username) {

		try (Connection conn = DatabaseConnection.connect()) {
			String sql = "SELECT * FROM items WHERE seller_username = ?";
			if (username.trim().isEmpty()) {
				sql = "SELECT * FROM items";
			}

			try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

				if (!username.trim().isEmpty()) {
					preparedStatement.setString(1, username);
				}

				ResultSet rows = preparedStatement.executeQuery();
				Item i;
				while (rows.next()) {
					i = (new Item(rows.getInt("id"), rows.getString("name"), rows.getString("seller_username"),
							rows.getString("bidder_username"), rows.getDouble("price"), rows.getString("type"),
							rows.getString("created_at"), rows.getString("description"), rows.getDouble("shipping"),
							rows.getDouble("purchase_amount")));
					if (rows.getInt("sold") == 1) {
						i.setSold(true);
					} else {
						i.setSold(false);
					}

					items.add(i);
				}

				preparedStatement.close();
				conn.close();
			}

			// response.sendRedirect("Search.html");

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return items;
	}

	public List<Item> readAllSearch(String searchText) {

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

			// createItemsTable();

			ResultSet rows = preparedStatement.executeQuery();

			items = new ArrayList<>();

			while (rows.next()) {

				items.add(new Item(rows.getInt("id"), rows.getString("name"), rows.getString("seller_username"),
						rows.getString("bidder_username"), rows.getDouble("price"), rows.getString("type"),
						rows.getString("created_at"), rows.getString("description"), rows.getDouble("shipping"),
						rows.getDouble("purchase_amount")));
			}

			preparedStatement.close();
			conn.close();

		} catch (

		SQLException e) {
			e.printStackTrace();
		}
		return items;
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

				// Define the desired date-time forma
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

				// Format the current date and time
				String formattedDateTime = currentDateTime.format(formatter);

				preparedStatement.setString(7, formattedDateTime);

				preparedStatement.executeUpdate();
				preparedStatement.close();
				conn.close();
			}

			// response.sendRedirect("Search.html");

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
						rows.getString("created_at"), rows.getString("description"), rows.getDouble("shipping"),
						rows.getDouble("purchase_amount")));
			}

			preparedStatement.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return i;
	}


	public void delete(int id) {
		items.removeIf(s -> s.getId() == id);
	}

	public void forwardAuction(String item_id, String username, double amount) {
		try (Connection conn = DatabaseConnection.connect(); Statement statement = conn.createStatement()) {
			if (!isGreater(item_id, amount, conn)) {
				System.out.println("nee");
				return;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		try (Connection conn = DatabaseConnection.connect(); Statement statement = conn.createStatement()) {

			System.out.println("ID" + item_id);

			System.out.println("nee");
			String sql = "UPDATE items SET bidder_username = ?, price = ? WHERE id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setString(1, username);
			preparedStatement.setDouble(2, amount);
			preparedStatement.setString(3, item_id);

			preparedStatement.executeUpdate();

			preparedStatement.close();
			conn.close();
			// response.sendRedirect("Search.html");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return;

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
		return;
	}
	

	public void japaneseAuction(String item_Id, String username, String current_Value) {
	    try (Connection conn = DatabaseConnection.connect(); Statement statement = conn.createStatement()) {
	        String sql = "SELECT * FROM items WHERE id = ? AND sold = 0";

	        PreparedStatement preparedStatement = conn.prepareStatement(sql);
	        preparedStatement.setString(1, item_Id);

	        ResultSet rows = preparedStatement.executeQuery();

	        if (rows.next()) {
	            String createdAt = rows.getString("created_at");
	            String type = rows.getString("type");
	            double price = rows.getInt("price");

	            Item item = new Item(createdAt, type, price);


	            System.out.println(createdAt);
	            System.out.println(type);
	            System.out.println(price);
	            System.out.println("HELLO" + item.getIncrementalCost());
	            System.out.println(item.getCost());

	            String sql2 = "UPDATE items SET bidder_username = ?, price = ? WHERE id = ?";
	            preparedStatement = conn.prepareStatement(sql2);

	            preparedStatement.setString(1, username);
	            preparedStatement.setInt(2, Integer.parseInt(current_Value));
	            preparedStatement.setInt(3, Integer.parseInt(item_Id));

	            preparedStatement.executeUpdate();
	            setToSold(item_Id, conn);
	            preparedStatement.close();
	            conn.close();
	        }
	        
	        preparedStatement.close();
            conn.close();

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
					preparedStatement.close();
					conn.close();
					return true;
				}
				preparedStatement.close();
				conn.close();
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

	public void purchase(int item_id, String username, String total) {

		try (Connection conn = DatabaseConnection.connect(); Statement statement = conn.createStatement()) {

			String sql = "UPDATE items SET purchase_amount = ? WHERE id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			


			preparedStatement.setString(1, total);
			preparedStatement.setInt(2, item_id);

			preparedStatement.executeUpdate();
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
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
				String type = rows.getString("type");
				double price = rows.getInt("price");

				Item item = new Item(createdAt, type, price);

				System.out.println(createdAt);
				System.out.println(type);
				System.out.println(price);
				System.out.println(item.getRemainingTime());
				System.out.println(item.getType());
				System.out.println(item.getCost());
				
				

				if (item.getType().equals("dutch") && item.getCost() > 5) {
					System.out.println("done");
					preparedStatement.close();
					conn.close();

					return true;
				} else if (item.getRemainingTime() <= 0) {
					setToSold(item_id, conn);
					System.out.println(item_id + "good");
					preparedStatement.close();
					conn.close();

					return false;
				} else {
					preparedStatement.close();
					conn.close();

					return true;
				}

			} else {
				preparedStatement.close();
				conn.close();

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
			preparedStatement.close();
			statement.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	

}
