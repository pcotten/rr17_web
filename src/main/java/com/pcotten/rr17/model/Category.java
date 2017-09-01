package com.pcotten.rr17.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Category {

	private Integer id;
	@NotNull @Size(min=3, max=45)
	private String name;
	@Size(max=255)
	private String description;
	
	public Category(){
		
	}
	
	public Category(String name, String description){
		this.name = name;
		this.description = description;
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
	
	
	
}
