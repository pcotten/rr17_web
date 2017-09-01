package com.pcotten.rr17.model;

import java.util.ArrayList;
import java.util.List;

public class Cookbook {

	private Integer id;
	private String title;
	private List<Integer> recipes;
	private List<Integer> categories;
	private Integer creatorId;
	
	
	public Cookbook() {
		super();
		this.recipes = new ArrayList<Integer>();
		this.categories = new ArrayList<Integer>();
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

	public List<Integer> getRecipes() {
		return recipes;
	}
	public void setRecipes(List<Integer> recipes) {
		this.recipes = recipes;
	}

	public List<Integer> getCategories() {
		return categories;
	}

	public void setCategories(List<Integer> categories) {
		this.categories = categories;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}
	
	
}
