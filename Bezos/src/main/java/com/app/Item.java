package com.app;

import java.sql.Date;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Item {

	private int id=0;
	private String name="";
	private String type="";
	private double price=0;
	// @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Timestamp date;
	private String description="";
    private double shipping=0;

	public Item() {
		super();
	}

	public Item(int id, String name, double price, String type, Timestamp date, String description, double shipping) {
		setId(id);
		setName(name);
		setPrice(price);
		setType(type);
		setDate(date);
		setDescription(description);
		setShipping(shipping);

	}

	public Item(String name, double price, String type, Timestamp date, String description, double shipping) {
		setName(name);
		setPrice(price);
		setType(type);
		setDate(date);
		setId(0);
		setDescription(description);
		setShipping(shipping);
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
	
	public double getShipping() {
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
	
	public void setShipping(double shipping) {
		this.shipping = shipping;

	}
}
