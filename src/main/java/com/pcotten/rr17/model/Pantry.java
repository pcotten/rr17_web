package com.pcotten.rr17.model;

import java.util.ArrayList;
import java.util.List;

public class Pantry {

	private Integer id;
	private String pantryCode;
	private String description;
	private List<Integer> users;
	private List<Ingredient> ingredients;
	
	public Pantry(){
		
	}
	
	public Pantry(Integer id) {
		super();
		this.id = id;
		this.ingredients = new ArrayList<Ingredient>();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getPantryCode() {
		return pantryCode;
	}

	public void setPantryCode(String pantryCode) {
		this.pantryCode = pantryCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Integer> getUsers() {
		return users;
	}
	
	public void setUsers(List<Integer> users) {
		this.users = users;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
}
