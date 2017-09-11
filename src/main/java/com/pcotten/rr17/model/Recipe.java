package com.pcotten.rr17.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Recipe {
	
	private Integer id;
	@NotNull @Size(max=255)
	private String title;
	@Size(max=255)
	private String description;
	private Integer owner;  // userId of user acct that created recipe
	@Size(max=30)
	private String attributedTo;
	private Integer numberOfServings;
	private Integer ovenTemp;
	private Integer servingSize;
	private String servingSizeUnit;
	private Integer cookTime;
	private String cookTimeUnit;
	private Integer prepTime;
	private String prepTimeUnit;
	private Integer rating;
	private Date lastPrepared;
	
	private Map<String, Map<String, Object>> ingredients;  // name, quantity, quantityUnit
	private Map<Integer, String> instructions; // orderIndex, text
	private List<String> images;
	private List<String> categories;

	public Recipe(){
		init();
	}
	
	public Recipe(String title) {
		super();
		init();
		this.title = title;
	}
	
	public void init(){
		this.ingredients = new HashMap<String, Map<String, Object>>();
		this.instructions = new HashMap<Integer, String>();
		this.images = new ArrayList<String>();
		this.categories = new ArrayList<String>();
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getServings() {
		return numberOfServings;
	}
	public void setServings(Integer servings) {
		this.numberOfServings = servings;
	}
	
	public Integer getOvenTemp() {
		return ovenTemp;
	}
	public void setOvenTemp(Integer ovenTemp) {
		this.ovenTemp = ovenTemp;
	}
	
	public Map<String, Map<String, Object>> getIngredients() {
		return ingredients;
	}
	public void setIngredients(Map<String, Map<String, Object>> ingredients) {
		this.ingredients = ingredients;
	}

	public Map<Integer, String> getInstructions() {
		return instructions;
	}
	public void setInstructions(Map<Integer, String> instructions) {
		this.instructions = instructions;
	}
	
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}

	public Integer getOwner() {
		return owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}

	public String getAttributedTo() {
		return attributedTo;
	}

	public void setAttributedTo(String attributedTo) {
		this.attributedTo = attributedTo;
	}

	public Integer getNumberOfServings() {
		return numberOfServings;
	}

	public void setNumberOfServings(Integer numberOfServings) {
		this.numberOfServings = numberOfServings;
	}

	public Integer getServingSize() {
		return servingSize;
	}

	public void setServingSize(Integer servingSize) {
		this.servingSize = servingSize;
	}

	public Integer getCookTime() {
		return cookTime;
	}

	public void setCookTime(Integer cookTime) {
		this.cookTime = cookTime;
	}

	public Integer getPrepTime() {
		return prepTime;
	}

	public void setPrepTime(Integer prepTime) {
		this.prepTime = prepTime;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Date getLastPrepared() {
		return lastPrepared;
	}

	public void setLastPrepared(Date lastPrepared) {
		this.lastPrepared = lastPrepared;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public String getServingSizeUnit() {
		return servingSizeUnit;
	}

	public void setServingSizeUnit(String servingUnit) {
		this.servingSizeUnit = servingUnit;
	}

	public String getCookTimeUnit() {
		return cookTimeUnit;
	}

	public void setCookTimeUnit(String cookTimeUnit) {
		this.cookTimeUnit = cookTimeUnit;
	}

	public String getPrepTimeUnit() {
		return prepTimeUnit;
	}

	public void setPrepTimeUnit(String prepTimeUnit) {
		this.prepTimeUnit = prepTimeUnit;
	}
	
	
	
	
}
