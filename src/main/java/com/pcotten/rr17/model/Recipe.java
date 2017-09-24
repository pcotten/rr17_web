package com.pcotten.rr17.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
	private String ovenTemp;
	private Integer servingSize;
	private String servingSizeUnit;
	private Integer cookTime;
	private String cookTimeUnit;
	private Integer prepTime;
	private String prepTimeUnit;
	private Integer rating;
	private Date lastPrepared;
	
	private List<Ingredient> ingredients;  // name, quantity, quantityUnit
	private List<Instruction> instructions; // orderIndex, text
	private List<Image> images;
	private List<Category> categories;

	public Recipe(){
		init();
	}
	
	public Recipe(String title) {
		super();
		init();
		this.title = title;
	}
	
	public void init(){
		this.ingredients = new ArrayList<Ingredient>();
		this.instructions = new ArrayList<Instruction>();
		this.images = new ArrayList<Image>();
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

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public void setNumberOfServings(Integer servings) {
		this.numberOfServings = servings;
	}
	
	public String getOvenTemp() {
		return ovenTemp;
	}
	public void setOvenTemp(String ovenTemp) {
		this.ovenTemp = ovenTemp;
	}

	public Integer getServingSize() {
		return servingSize;
	}
	public void setServingSize(Integer servingSize) {
		this.servingSize = servingSize;
	}
	
	public String getServingSizeUnit() {
		return servingSizeUnit;
	}
	public void setServingSizeUnit(String servingUnit) {
		this.servingSizeUnit = servingUnit;
	}
	
	public Integer getCookTime() {
		return cookTime;
	}
	public void setCookTime(Integer cookTime) {
		this.cookTime = cookTime;
	}
	
	public String getCookTimeUnit() {
		return cookTimeUnit;
	}
	public void setCookTimeUnit(String cookTimeUnit) {
		this.cookTimeUnit = cookTimeUnit;
	}
	
	public Integer getPrepTime() {
		return prepTime;
	}
	public void setPrepTime(Integer prepTime) {
		this.prepTime = prepTime;
	}
	
	public String getPrepTimeUnit() {
		return prepTimeUnit;
	}
	public void setPrepTimeUnit(String prepTimeUnit) {
		this.prepTimeUnit = prepTimeUnit;
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
	
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public List<Instruction> getInstructions() {
		return instructions;
	}
	public void setInstructions(List<Instruction> instructions) {
		this.instructions = instructions;
	}
	
	public List<Image> getImages() {
		return images;
	}
	public void setImages(List<Image> images) {
		this.images = images;
	}

	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}






	
	
	
	
}
