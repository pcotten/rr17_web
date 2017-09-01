package com.pcotten.rr17.model;

public class Image {
	
	private Integer id;
	private String imagePath;
	private Integer recipeId;
	private Integer userId;
	private String text;
	
	public Image(){
		
	}
	
	public Image(String imagePath, Integer recipeId, String imageText) {
		super();
		this.imagePath = imagePath;
		this.recipeId = recipeId;
		this.text = imageText;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer imageId) {
		this.id = imageId;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public Integer getRecipeId() {
		return recipeId;
	}
	public void setRecipeId(Integer recipeId) {
		this.recipeId = recipeId;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String imageText) {
		this.text = imageText;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}
