package com.pcotten.rr17.model;

import java.util.ArrayList;
import java.util.List;

public class Cookbook {

	private Integer id;
	private String title;
	private List<Recipe> recipes;
	private List<Category> categories;
	private Integer creatorId;
	
	
	public Cookbook() {
		super();
		this.recipes = new ArrayList<Recipe>();
		this.categories = new ArrayList<Category>();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}
	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}
	
	
}
