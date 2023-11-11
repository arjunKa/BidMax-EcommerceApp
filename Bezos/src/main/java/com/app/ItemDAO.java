package com.app;

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
		return Items.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
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