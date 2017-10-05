package com.pcotten.rr17.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Ingredient {

	private Integer id;
	@NotNull @Size(min=2, max=45)
	private String name;
	@Size(max=255)
	private String description;
	private Integer pantryId;
	private Image image;
	private Float quantity;
	private String quantityDisplay;
	private String quantityUnit;
	private Integer groupIndex;
	private String groupName;
	private List<Category> categories;
	
	public Ingredient(){
		
	}
	
	public Ingredient(String name, String description) {
		super();
		this.name = name;
		this.description = description;
		this.categories = new ArrayList<Category>();
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

	public Integer getPantryId() {
		return pantryId;
	}

	public void setPantryId(Integer pantryId) {
		this.pantryId = pantryId;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Float getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}
	
	public String getQuantityDisplay() {
		return quantityDisplay;
	}

	public void setQuantityDisplay(String quantityDisplay) {
		this.quantityDisplay = quantityDisplay;
	}

	public String getQuantityUnit() {
		return quantityUnit;
	}

	public void setQuantityUnit(String quantityUnit) {
		this.quantityUnit = quantityUnit;
	}

	public Integer getGroupIndex() {
		return groupIndex;
	}

	public void setGroupIndex(Integer groupOrderIndex) {
		this.groupIndex = groupOrderIndex;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
}
