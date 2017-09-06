package com.pcotten.rr17.model;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Meal {

	private Integer id;
	@NotNull @Size(min=3, max=255)
	private String name;
	private List<Recipe> recipes;
	private Integer ownerId;
	private LocalDate lastPrepared;
	
	public Meal(){
		
	}
	
	public Meal(String name) {
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
	
	public List<Recipe> getRecipes() {
		return recipes;
	}
	
	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}
	
	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public LocalDate getLastPrepared() {
		return lastPrepared;
	}
	
	public void setLastPrepared(LocalDate lastPrepared) {
		this.lastPrepared = lastPrepared;
	}
	
	
	
}
