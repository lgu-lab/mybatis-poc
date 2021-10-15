package org.demo.domain;

import java.util.LinkedList;
import java.util.List;

public class Month {
	private int year;
	private int month ;
	private String name;
	private boolean open;

	// OneToMany relation
	private List<Order> orderList = new LinkedList<>();
	
	public Month() {
		super();
	}

	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}

	public List<Order> getOrders() {
		return orderList;
	}
	
	@Override
	public String toString() {
		return "Month [" + year + "/" + month + " : name=" + name + ", open=" + open 
				+ ", orders count=" + orderList.size() + "]";
	}

}