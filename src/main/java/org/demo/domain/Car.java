package org.demo.domain;

import java.math.BigDecimal;

public class Car {
	// private fields : same names as COLUMNS NAMES in the SQL table
	// usable by MyBatis (by introspection)
	private Integer    car_id;
	private String     car_name;
	private BigDecimal car_price;
	private boolean    car_ok;
	
	public Car() {
		super();
	}
	
	public Integer getId() {
		return car_id;
	}
	public void setId(Integer id) {
		this.car_id = id;
	}

	public String getName() {
		return car_name;
	}
	public void setName(String name) {
		this.car_name = name;
	}

	public BigDecimal getPrice() {
		return car_price;
	}
	public void setPrice(BigDecimal price) {
		this.car_price = price;
	}

	public boolean isOk() {
		return car_ok;
	}
	public void setOk(boolean ok) {
		this.car_ok = ok;
	}

	@Override
	public String toString() {
		return "Car [id=" + car_id + ", name=" + car_name + ", price=" + car_price + ", ok=" + car_ok + "]";
	}

}