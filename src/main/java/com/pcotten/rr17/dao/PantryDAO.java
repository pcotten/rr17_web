package com.pcotten.rr17.dao;

import java.util.List;

import com.pcotten.rr17.model.Pantry;
import com.pcotten.rr17.model.User;

public interface PantryDAO {
	
	public Pantry getPantry(Integer id);
	
	public Pantry createPantry(Pantry pantry);
	
	public Integer updatePantry(Pantry pantry);
	
	public Integer deletePantry(Integer id);
	
	public Pantry getUserPantry(Integer userId);
	
	public List<User> getPantryUsers(Integer pantryId);
	
	public List<Pantry> getPantries();
		
}
