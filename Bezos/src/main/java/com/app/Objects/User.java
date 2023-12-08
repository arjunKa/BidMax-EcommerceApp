package com.app.Objects;

public class User {
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String address;
	private String postalCode;
	private String city;
	private String country;
	private String username;
	private String password;

	public User(int id, String firstName, String lastName, String email, String address, String postalCode, String city,
			String country, String username) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
		this.username = username;

	}

	public User(String firstName, String lastName, String email, String address, String postalCode, String city,
			String country, String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
		this.username = username;
		this.password = password;

	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;

	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	// Getters
	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getAddress() {
		return address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	// Setters
	public void setId(int id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
