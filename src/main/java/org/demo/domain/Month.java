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
	
	// ManyToOne relation (owning side with FK)
	private int commentId; // FK to Comment
	private Comment refComment;
	
	private Year refYear; // FK is year (part of PK)
	
	public Month(int year, int month) {
		super();
		this.year = year;
		this.month = month;
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
	
	
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	// Object referenced from FK
	public Comment getRefComment() {
		return refComment;
	}

	// Object referenced from FK
	public Year getRefYear() {
		return refYear;
	}

	@Override
	public String toString() {
		return "Month : [" + year + "/" + month + "] : name=" + name + ", open=" + open 
				+ ", comment id : " + commentId
				+ "\n"
				+ "   --> @One  refComment : " + refComment
				+ "\n"
				+ "   --> @One  refYear : " + refYear
				+ "\n"
				+ "   --> @Many orderList size : " + orderList.size() 
				;
	}

}