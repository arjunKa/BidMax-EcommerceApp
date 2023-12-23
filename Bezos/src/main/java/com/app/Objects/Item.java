package com.app.Objects;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class Item {

	private int id = 0;
	private String name = "";
	private String sellerUsername = "";
	private String bidderUsername = "";
	private String type = "";
	private double price = 0;
	// @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private String date = "2023-12-07 18:00:00.0";
	private String description = "";
	private double shipping = 0;
	private double expeditedShipping = 35;
	private int remainingTime = 0;
	private int incrementalCost = (int) price;
	private boolean sold = false;
	private double purchaseAmount = 0;
	private double origCost = 0;

	public Item() {
		super();
	}

	public Item(String date, String type, double price) {
		setDate(date);
		setType(type);
		setPrice(price);
		setIncrementalCost(price);
	}

	public Item(int id, String name, String seller, String bidder, double price, String type, String date,
			String description, double shipping, double purchaseAmount) {
		setId(id);
		setName(name);
		setPrice(price);
		setType(type);
		setDate(date);
		setDescription(description);
		setShipping(shipping);
		setSellerUsername(seller);
		setBidderUsername(bidder);
		setPurchaseAmount(purchaseAmount);
		origCost = price;

	}

	public Item(int id, String name, double price, String type, String string, String description, double shipping) {
		setId(id);
		setName(name);
		setPrice(price);
		setType(type);
		setDate(string);
		setDescription(description);
		setShipping(shipping);
		origCost = price;

	}

	public Item(String name, double price, String type, String date, String description, double shipping) {
		setName(name);
		setPrice(price);
		setType(type);
		setDate(date);
		setId(0);
		setDescription(description);
		setShipping(shipping);
		origCost = price;
	}

	// Getters
	public int getId() {
		return id;
	}

	public String getSellerUsername() {
		return sellerUsername;
	}

	public String getBidderUsername() {
		return bidderUsername;
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

	public String getDate() {
		return date;
	}

	public String getDescription() {
		return description;

	}

	public double getShipping() {
		return shipping;
	}

	public double getExpeditedShipping() {
		return expeditedShipping;
	}

	public boolean getSold() {
		return sold;

	}

	public double getPurchaseAmount() {
		return purchaseAmount;
	}

	// Setters
	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSellerUsername(String name) {
		this.sellerUsername = name;
	}

	public void setBidderUsername(String name) {
		this.bidderUsername = name;
	}

	public void setPrice(double cost) {
		this.price = cost;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setDate(String date) {
		this.date = date;

	}

	public void setDescription(String description) {
		this.description = description;

	}

	public void setShipping(double shipping) {
		this.shipping = shipping;

	}

	public void setRemainingTime(int remTime) {
		this.remainingTime = remTime;

	}

	public void setIncrementalCost(double price2) {
		this.incrementalCost = (int) price2;

	}

	public void setSold(boolean sold) {
		this.sold = sold;

	}

	public void setPurchaseAmount(double purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	public int getRemainingTime() {
		if (this.type.equals("dutch") && this.price > 5) {
			remainingTime = 120;
		} else {
			calculateRemainingTime();
		}
		return remainingTime;
	}

	public int getIncrementalCost() {
		calculateCost();
		return incrementalCost;
	}

	public void calculateRemainingTime() {

		// Define the date-time formatter with the pattern
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
		LocalDateTime parsedDateTime = LocalDateTime.parse(date, formatter);

		// Convert LocalDateTime to Instant
		Instant instant = parsedDateTime.toInstant(ZoneOffset.UTC);

		// Get the epoch milliseconds from Instant
		long epochMillis = instant.toEpochMilli();

		LocalDateTime currentDateTime = LocalDateTime.now();

		// Extract the time in milliseconds since the epoch
		long difference = epochMillis - currentDateTime.toInstant(ZoneOffset.UTC).toEpochMilli() + 120000L;
		this.remainingTime = (int) difference / 1000;
		if (remainingTime < 0) {
			this.remainingTime = 0;
		}

	}

	public void calculateRemainingCost() {
		calculateRemainingTime();
		// Define the date-time formatter with the pattern
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

		// Parse the string to obtain a LocalDateTime object
		LocalDateTime parsedDateTime = LocalDateTime.parse(date, formatter);

		// Convert LocalDateTime to Instant
		Instant instant = parsedDateTime.toInstant(ZoneOffset.UTC);

		LocalDateTime currentDateTime = LocalDateTime.now();

		// Extract the time in milliseconds since the epoch
		int difference = (int) (instant.toEpochMilli() - currentDateTime.toInstant(ZoneOffset.UTC).toEpochMilli()
				+ 120000L) / 1000;
		if (difference < 100) {
			this.price = origCost + 50;
		} else if (difference < 80) {
			this.price = origCost + 50;
		} else if (difference < 60) {
			this.price = origCost + 50;
		} else if (difference < 40) {
			this.price = origCost + 50;
		} else if (difference < 20) {
			this.price = origCost + 50;
		} else {
			this.price = origCost + 50;
		}

	}

	public void calculateCost() {

		// Define the date-time formatter with the pattern
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

		// Parse the string to obtain a LocalDateTime object
		LocalDateTime parsedDateTime = LocalDateTime.parse(date, formatter);

		// Convert LocalDateTime to Instant
		Instant instant = parsedDateTime.toInstant(ZoneOffset.UTC);

		LocalDateTime currentDateTime = LocalDateTime.now();

		// Extract the time in milliseconds since the epoch
		long difference = instant.toEpochMilli() - currentDateTime.toInstant(ZoneOffset.UTC).toEpochMilli() + 120000L;
		this.remainingTime = (int) difference / 1000;

		this.incrementalCost = Math.abs(this.remainingTime - 120) * 10 + (int) this.price;

		if (this.remainingTime < 0) {
			this.remainingTime = 0;
		}

	}

}
