package com.pcotten.rr17.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Instruction {

	private Integer id;
	private Integer orderIndex;
	@NotNull @Size(max=255)
	private String text;
	private Integer recipeId;
	
	public Instruction(){
		
	}
	
	public Instruction(Integer orderIndex, String text, Integer recipeId) {
		super();
		this.orderIndex = orderIndex;
		this.text = text;
		this.recipeId = recipeId;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer instructionId) {
		this.id = instructionId;
	}
	
	public Integer getOrderIndex() {
		return orderIndex;
	}
	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public Integer getRecipeId() {
		return recipeId;
	}
	public void setRecipeId(Integer recipeId) {
		this.recipeId = recipeId;
	}
	
}
