package com.pcotten.rr17.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.inject.Inject;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;

import com.pcotten.rr17.storage.service.DatabaseManager;
import com.pcotten.rr17.dao.IngredientDAO;
import com.pcotten.rr17.dao.PantryDAO;
import com.pcotten.rr17.model.Ingredient;
import com.pcotten.rr17.model.Pantry;
import com.pcotten.rr17.service.PantryService;

@Component
public class PantryServiceImpl implements PantryService{

	@Inject
	DatabaseManager manager;
	@Inject
	PantryDAO pantryDAO;
	@Inject
	IngredientDAO ingredientDAO;
	
	Connection conn = null;
	Statement stmt = null;
	String sql = null;
	
	public PantryServiceImpl(){
		
	}

	public Pantry getPantryById(Integer id) {
		
		return pantryDAO.getPantry(id);
	}
	
	public Pantry createPantry(Pantry pantry) throws SQLException{
		
		
		if (pantry.getPantryCode() == null){
			pantry.setPantryCode(generatePantryCode());
		}

		pantry = pantryDAO.createPantry(pantry);

		if (pantry.getId() != null){
			System.out.println("New pantry successfully created in database with id " + pantry.getId());
		}
		else System.out.println("Pantry not created");
			
		return pantry;
	}
	
	public boolean updatePantry(Pantry pantry){
		
		boolean success = false;
		
		int result = pantryDAO.updatePantry(pantry);
		
		if (result != 0){
			System.out.println("Pantry updated in database");
			success = true;
		}
		else {
			System.out.println("Pantry not updated");	
		}
		return success;
	}
	
	public boolean deletePantry(Integer id) throws SQLException{
		
		boolean success = false;

		int result = pantryDAO.deletePantry(id);
		
		if (result != -1){
			System.out.println("Successfully removed pantry with id " + id);
			success = true;
		}
		else {
			System.out.println("Unable to remove pantry entity with id " + id);
		}
		
		return success;
	}
	
	private String generatePantryCode(){
		String newCode = null;
		boolean unique = true;
		boolean complete = false;
		List<Pantry> pantryList = pantryDAO.getPantries();
		while (!complete){
			newCode = RandomStringUtils.randomAlphanumeric(8);
			newCode = newCode.substring(0, 4) + "-" + newCode.substring(4);
			if (!pantryList.isEmpty())
				for (Pantry p: pantryList){
					if (p.getPantryCode().equals(newCode)){
						unique = false;
						break;
					}
					if (pantryList.indexOf(p) == pantryList.size()-1){
						unique = true;
					}
				}
			if (unique == true){
				complete = true;
			}
		}
		
		return newCode;
	}

	
	@Override
	public boolean pantryIngredientExists(Integer userId, Ingredient ingredient) {
		
		conn = manager.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("SELECT COUNT(*) FROM ingredients_by_pantryid "
					+ "WHERE name = ? AND pantryId = ?");
			pstmt.setString(1, ingredient.getName());
			pstmt.setInt(2, getPantryId(userId));
		} catch (SQLException e) {
			e.printStackTrace();
		}
			return manager.isExists(pstmt);
	
	}
	
	public Integer getPantryId(Integer userId) {
		Integer pantryId = null;
		try {
			conn = manager.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement("SELECT pantryId FROM user_pantry WHERE userId = ?");
			pstmt.setInt(1, userId);
			
			ResultSet result = pstmt.executeQuery();
			if (result.next()) {
				pantryId = result.getInt("pantryId");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pantryId;
	}

	@Override
	public List<Ingredient> getPantryIngredients(Integer userId) {
		Integer pantryId = getPantryId(userId);
		List<Ingredient> ingredients = ingredientDAO.getPantryIngredients(pantryId);
		
		return ingredients;
	}
	

	@Override
	public boolean createPantryIngredient(Ingredient ingredient, Integer userId) {

		boolean success = false;
		
		int result = ingredientDAO.createPantryIngredient(ingredient, getPantryId(userId));
		
		if (result > 0) {
			System.out.println("Successfully added " + ingredient.getName() + " to pantry.");
			success = true;
		}
		else {
			System.out.println("Failed to add ingredient to pantry");
		}
		
		return success;
	}

	@Override
	public boolean updatePantryIngredient(Integer userId, Ingredient ingredient) {
		
		boolean success = false;
		
		int result = ingredientDAO.updatePantryIngredient(getPantryId(userId), ingredient);
		
		if (result > 0 ) {
			System.out.println("Successfully updated ingredient");
			success = true;
		}
		else {
			System.out.println("Failed to update ingredient");
		}
		return success;
	}

	@Override
	public boolean deletePantryIngredient(Integer userId, Integer ingredientId) {
		
		boolean success = false;
		
		int result = ingredientDAO.deletePantryIngredient(getPantryId(userId), ingredientId);
		
		if (result > 0 ) {
			System.out.println("Successfully updated ingredient");
			success = true;
		}
		else {
			System.out.println("Failed to update ingredient");
		}
		return success;
	}

}
