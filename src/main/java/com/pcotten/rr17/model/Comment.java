package com.pcotten.rr17.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Comment {

	private Integer id;
	private Integer userId;
	private String username;
	private Integer parentId;
	private Integer recipeId;
	private String text;
	private List<Integer> comments;
	private LocalDate timestamp;
	
	public Comment(){
		
	}
	
	public Comment(Integer id, Integer userId, String username, Integer parentId, Integer recipeId, String text) {
		super();
		this.id = id;
		this.userId = userId;
		this.username = username;
		this.parentId = parentId;
		this.recipeId = recipeId;
		this.text = text;
		this.comments = new ArrayList<Integer>();
		this.timestamp = LocalDate.now();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
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

	public void setText(String text) {
		this.text = text;
	}

	public List<Integer> getComments() {
		return comments;
	}

	public void setComments(List<Integer> comments) {
		this.comments = comments;
	}

	public LocalDate getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDate timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
