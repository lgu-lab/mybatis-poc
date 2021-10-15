package org.demo.domain;

import java.math.BigDecimal;

public class Order {
	private int id;
	private String name;
	private BigDecimal price;
	private String customer;
	
	private int year; // FK
	private short month ; // FK
	
	public Order() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public short getMonth() {
		return month;
	}

	public void setMonth(short month) {
		this.month = month;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", name=" + name + ", price=" + price + ", customer=" + customer + ", year=" + year
				+ ", month=" + month + "]";
	}

}