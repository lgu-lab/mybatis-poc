package org.demo.domain;

public class Year {
	private int year;
	private String name;

	public Year() {
		super();
	}

	public Year(int year, String name) {
		super();
		this.year = year;
		this.name = name;
	}

	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Year [year=" + year + ", name=" + name + "]";
	}

}