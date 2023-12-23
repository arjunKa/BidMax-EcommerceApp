package com.app;

import java.sql.Date;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Item {

	private int id;
	private String name;
	private String type;
	private double price;
	// @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Timestamp date;
	private String description;
    private int shipping;

	public Item() {
		super();
	}

	public Item(int id, String name, double price, String type, Timestamp date, String description, int shipping) {
		setId(id);
		setName(name);
		setPrice(price);
		setType(type);
		setDate(date);

	}

	public Item(String name, double price, String type, Timestamp date) {
		setName(name);
		setPrice(price);
		setType(type);
		setDate(date);
		setId(0);
	}

	// Getters
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getCost() {
		return price;
	}

	public String getType() {
		return type;
	}

	public Timestamp getDate() {
		return date;
	}
	
	public String getDescription() {
		return description;

	}
	
	public int getShipping() {
		return shipping;
	}

	// Setters
	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(double cost) {
		this.price = cost;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setDate(Timestamp date) {
		this.date = date;

	}
	
	public void setDescription(String description) {
		this.description = description;

	}
	
	public void setShipping(int shipping) {
		this.shipping = shipping;

	}
}
