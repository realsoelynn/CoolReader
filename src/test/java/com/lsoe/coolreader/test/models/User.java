package com.lsoe.coolreader.test.models;

import com.lsoe.coolreader.test.enums.Country;

/**
 * TODO: Describe purpose and behavior of User
 */
public class User {

	private String name;
	private double weight;
	private boolean isMale;
	private int height;
	private Country country;

	public User(String name, double weight, boolean isMale, int height,
			Country country) {

		this.name = name;
		this.weight = weight;
		this.isMale = isMale;
		this.height = height;
		this.country = country;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public boolean isMale() {
		return isMale;
	}
	public void setMale(boolean isMale) {
		this.isMale = isMale;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}

}
