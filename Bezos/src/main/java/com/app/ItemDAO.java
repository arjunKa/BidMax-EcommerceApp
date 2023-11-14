package com.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

	private static List<Item> Items = new ArrayList<>();

	public Item create(Item item) {
		Items.add(item);
		return item;
	}

	public List<Item> readAll() {
		return Items;
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
				i = (new Item(rows.getInt("id"), rows.getString("name"), rows.getDouble("price"),
						rows.getString("type"), rows.getTimestamp("endtime"), rows.getString("description"),
						rows.getDouble("shipping")));
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
}