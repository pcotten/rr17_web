package com.pcotten.rr17.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MealPlan {

	private Integer id;
	@NotNull @Size(min=3, max=255)
	private String name;
	@Size(max=255)
	private String description;
	private List<Meal> meals; // date as string, Meal
	private Integer owner;
	
	public MealPlan(){
		meals = new ArrayList<Meal>();
	}
	
	public MealPlan(String name) {
		super();
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Meal> getMeals() {
		return meals;
	}
	public void setMeals(List<Meal> meals) {
		this.meals = meals;
	}

	public Integer getOwner() {
		return owner;
	}

	public void setOwner(Integer ownerId) {
		this.owner = ownerId;
	}
	
	
}
