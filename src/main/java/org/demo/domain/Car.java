package org.demo.domain;

import java.math.BigDecimal;

public class Car {
	private Integer car_id;
	private String name;
	private BigDecimal price;
	private boolean ok;

	
	public Car() {
		super();
	}
	
	public Integer getId() {
		return car_id;
	}
	public void setCarId(Integer id) {
		this.car_id = id;
	}

	private String getCar_Name() {
		return name;
	}
	public void setCar_Name(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public boolean isOk() {
		return ok;
	}
	public void setOk(boolean ok) {
		this.ok = ok;
	}

	@Override
	public String toString() {
		return "Car [id=" + car_id + ", name=" + name + ", price=" + price + ", ok=" + ok + "]";
	}

}